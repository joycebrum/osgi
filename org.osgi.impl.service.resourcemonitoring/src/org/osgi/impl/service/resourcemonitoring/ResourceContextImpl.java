
package org.osgi.impl.service.resourcemonitoring;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.osgi.impl.service.resourcemonitoring.bundlemanagement.BundleHolder;
import org.osgi.impl.service.resourcemonitoring.bundlemanagement.BundleManager;
import org.osgi.impl.service.resourcemonitoring.bundlemanagement.BundleManagerException;
import org.osgi.service.resourcemonitoring.ResourceContext;
import org.osgi.service.resourcemonitoring.ResourceContextEvent;
import org.osgi.service.resourcemonitoring.ResourceContextException;
import org.osgi.service.resourcemonitoring.ResourceMonitor;
import org.osgi.service.resourcemonitoring.ResourceMonitorException;

/**
 * Implementation of ResourceContext.
 * 
 * @author Gregory BONNARDEL (Orange)
 */
public class ResourceContextImpl implements ResourceContext, BundleHolder {

	/**
	 * Resource Context name.
	 */
	private final String						name;

	/**
	 * Bundles belonging to the Context. List<Long> bundles.
	 */
	private final List							bundles;

	/**
	 * Resource Monitors associated to the context. Map<String, ResourceMonitor>
	 * monitors.
	 */
	private final Map							monitors;

	/**
	 * notifier for ResourceContextEvent.
	 */
	private final ResourceContextEventNotifier	eventNotifier;

	/**
	 * ResourceMonitoringServiceImpl.
	 */
	private final ResourceMonitoringServiceImpl	resourceMonitoringServiceImpl;

	/**
	 * bundle manager service
	 */
	private final BundleManager					bundleManager;

	/**
	 * is true if the current context has been deleted (i.e.
	 * {@link #removeContext(ResourceContext)} has been previously called).
	 */
	private boolean								isRemoved	= false;

	/**
	 * Default constructor to be used to create new context.
	 * 
	 * @param resourceMonitoringServiceImpl resourceMonitoringServiceImpl (the
	 *        one calling this method)
	 * @param pBundleManager bundle manager
	 * @param pName name of the ResourceContext
	 * @param pEventNotifier event notifier
	 */
	public ResourceContextImpl(final ResourceMonitoringServiceImpl resourceMonitoringServiceImpl,
			final BundleManager pBundleManager, final String pName,
			final ResourceContextEventNotifier pEventNotifier) {
		this.resourceMonitoringServiceImpl = resourceMonitoringServiceImpl;
		bundleManager = pBundleManager;
		eventNotifier = pEventNotifier;
		name = pName;
		bundles = new ArrayList();
		monitors = new Hashtable();
	}

	public String getName() {
		return name;
	}

	public void addBundle(long bundleId) throws ResourceContextException {
		// check the Resource Context exist
		checkResourceContextExistency();

		// delegates bundle adding to the bundle manager
		try {
			bundleManager.addBundleToHolder(bundleId, this);
		} catch (BundleManagerException e) {
			throw new ResourceContextException(e.getMessage(), e);
		}

		// create event and notify
		ResourceContextEvent event = new ResourceContextEvent(
				ResourceContextEvent.BUNDLE_ADDED, this, bundleId);
		eventNotifier.notify(event);

	}

	public void removeBundle(long bundleId) throws ResourceContextException {
		checkResourceContextExistency();

		// delegates bundle removing to the bundle manager
		try {
			bundleManager.removeBundleFromHolder(bundleId, this);
		} catch (BundleManagerException e) {
			throw new ResourceContextException(e.getMessage(), e);
		}

		// create event and notify
		ResourceContextEvent event = new ResourceContextEvent(
				ResourceContextEvent.BUNDLE_REMOVED, this, bundleId);
		eventNotifier.notify(event);

	}

	public void removeBundle(long bundleId, ResourceContext destination) throws ResourceContextException {
		removeBundle(bundleId);

		// if destination is not null, associate the bundle to the destination
		// resource context
		if (destination != null) {
			destination.addBundle(bundleId);
		}

	}

	public synchronized ResourceMonitor getMonitor(String resourceType) throws ResourceContextException {
		checkResourceContextExistency();
		ResourceMonitor monitor = null;
		synchronized (monitors) {
			monitor = (ResourceMonitor) monitors.get(resourceType);
		}
		return monitor;
	}

	public synchronized void addResourceMonitor(ResourceMonitor resourceMonitor) throws ResourceContextException {

		// check the Resource Context is still existing
		checkResourceContextExistency();

		// check the Resource Monitor is not null
		if (resourceMonitor == null) {
			throw new NullPointerException("ResourceMonitor MUST not be null");
		}

		// check the Resource Monitor is associated to this context
		if (!resourceMonitor.getContext().equals(this)) {
			throw new ResourceContextException(
					"This ResourceMonitor is associated to another ResourceContext");
		}

		// add into the monitor map
		synchronized (monitors) {
			if (monitors.containsKey(resourceMonitor.getResourceType())) {
				// check there is no other monitor handling the same
				// resource type for this Resource Context
				throw new ResourceContextException(
						"A ResourceMonitor of the same type exists for this ResourceContext");
			}

			monitors.put(resourceMonitor.getResourceType(), resourceMonitor);
		}

	}

	public synchronized void removeResourceMonitor(ResourceMonitor resourceMonitor) throws ResourceContextException {
		// check the Resource Context is still existing
		checkResourceContextExistency();

		// check the Resource Monitor is not null
		if (resourceMonitor == null) {
			Logger.getLogger(this.getClass().getName()).finest("ResourceMonitor MUST not be null");
			return;
		}

		// check if the ResourceMonitor is associated with this Resource Context
		ResourceMonitor innerRm = getMonitor(resourceMonitor.getResourceType());
		if (!innerRm.equals(resourceMonitor)) {
			// TODO handle this kind of error
		}

		synchronized (monitors) {
			monitors.remove(resourceMonitor.getResourceType());
		}

	}

	public synchronized void removeContext(ResourceContext destination) throws ResourceContextException {
		// check the Resource Context is still existing
		checkResourceContextExistency();

		// delete all bundles
		List bs = new ArrayList();
		synchronized (bundles) {
			bs.addAll(bundles);
		}
		for (Iterator it = bs.iterator(); it.hasNext();) {
			try {
				Long bundleId = (Long) it.next();
				removeBundle(bundleId.longValue(), destination);
			} catch (ResourceContextException e) {
				// this exception can be thrown if the destination context has
				// been deleted
				e.printStackTrace();
			}
		}

		// delete all monitors
		List resourceMonitors = new ArrayList();
		synchronized (monitors) {
			resourceMonitors.addAll(monitors.values());
		}
		for (Iterator it = resourceMonitors.iterator(); it.hasNext();) {
			ResourceMonitor rm = (ResourceMonitor) it.next();
			try {
				rm.delete();
			} catch (ResourceMonitorException e) {
				throw new ResourceContextException(e.getMessage(), e);
			}
		}

		resourceMonitoringServiceImpl.removeContext(this);

		isRemoved = true;

	}

	/**
	 * Check Resource Context Existency
	 * 
	 * @throws ResourceContextException
	 */
	private synchronized void checkResourceContextExistency() throws ResourceContextException {
		if (isRemoved) {
			throw new ResourceContextException("ResourceContext has been removed.");
		}
	}

	public long[] getBundleIds() {
		long[] bundleIds = new long[bundles.size()];
		int i = 0;
		synchronized (bundles) {
			for (Iterator it = bundles.iterator(); it.hasNext();) {
				Long bundleId = (Long) it.next();
				bundleIds[i] = bundleId.longValue();
				i++;
			}
		}
		return bundleIds;
	}

	public ResourceMonitor[] getMonitors() throws ResourceContextException {
		checkResourceContextExistency();
		ResourceMonitor[] array = new ResourceMonitor[0];
		synchronized (monitors) {
			array = (ResourceMonitor[]) monitors.values().toArray(array);
		}
		return array;
	}

	public void addBundleToHolder(long bundleId) {
		synchronized (bundles) {
			bundles.add(Long.valueOf(Long.toString(bundleId)));
		}

	}

	public void removeBundleToHolder(long bundleId) {
		synchronized (bundles) {
			bundles.remove(Long.valueOf(Long.toString(bundleId)));
		}

	}

	/**
	 * Resource Context c1 is equals to ResourceContext c2 if c1.getName()
	 * equals c2.getName().
	 */
	public boolean equals(Object resourceContext) {
		if (resourceContext == null) {
			return false;
		}
		if (!(resourceContext instanceof ResourceContext)) {
			return false;
		}
		ResourceContext rc1 = (ResourceContext) resourceContext;
		return ((rc1.getName() != null) && rc1.getName().equals(getName()));
	}

}