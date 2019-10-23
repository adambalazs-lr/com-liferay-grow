/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.grow.gamification.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.grow.gamification.model.Badge;
import com.liferay.grow.gamification.service.BadgeLocalService;
import com.liferay.grow.gamification.service.persistence.BadgePersistence;
import com.liferay.grow.gamification.service.persistence.BadgeTypePersistence;
import com.liferay.grow.gamification.service.persistence.LDatePersistence;
import com.liferay.grow.gamification.service.persistence.SubscriberPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the badge local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.grow.gamification.service.impl.BadgeLocalServiceImpl}.
 * </p>
 *
 * @author Vilmos Papp
 * @see com.liferay.grow.gamification.service.impl.BadgeLocalServiceImpl
 * @see com.liferay.grow.gamification.service.BadgeLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class BadgeLocalServiceBaseImpl extends BaseLocalServiceImpl
	implements BadgeLocalService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.grow.gamification.service.BadgeLocalServiceUtil} to access the badge local service.
	 */

	/**
	 * Adds the badge to the database. Also notifies the appropriate model listeners.
	 *
	 * @param badge the badge
	 * @return the badge that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Badge addBadge(Badge badge) {
		badge.setNew(true);

		return badgePersistence.update(badge);
	}

	/**
	 * Creates a new badge with the primary key. Does not add the badge to the database.
	 *
	 * @param badgeId the primary key for the new badge
	 * @return the new badge
	 */
	@Override
	@Transactional(enabled = false)
	public Badge createBadge(long badgeId) {
		return badgePersistence.create(badgeId);
	}

	/**
	 * Deletes the badge with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param badgeId the primary key of the badge
	 * @return the badge that was removed
	 * @throws PortalException if a badge with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Badge deleteBadge(long badgeId) throws PortalException {
		return badgePersistence.remove(badgeId);
	}

	/**
	 * Deletes the badge from the database. Also notifies the appropriate model listeners.
	 *
	 * @param badge the badge
	 * @return the badge that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Badge deleteBadge(Badge badge) {
		return badgePersistence.remove(badge);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(Badge.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return badgePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.grow.gamification.model.impl.BadgeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return badgePersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.grow.gamification.model.impl.BadgeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return badgePersistence.findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return badgePersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return badgePersistence.countWithDynamicQuery(dynamicQuery, projection);
	}

	@Override
	public Badge fetchBadge(long badgeId) {
		return badgePersistence.fetchByPrimaryKey(badgeId);
	}

	/**
	 * Returns the badge matching the UUID and group.
	 *
	 * @param uuid the badge's UUID
	 * @param groupId the primary key of the group
	 * @return the matching badge, or <code>null</code> if a matching badge could not be found
	 */
	@Override
	public Badge fetchBadgeByUuidAndGroupId(String uuid, long groupId) {
		return badgePersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the badge with the primary key.
	 *
	 * @param badgeId the primary key of the badge
	 * @return the badge
	 * @throws PortalException if a badge with the primary key could not be found
	 */
	@Override
	public Badge getBadge(long badgeId) throws PortalException {
		return badgePersistence.findByPrimaryKey(badgeId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(badgeLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(Badge.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("badgeId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(badgeLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(Badge.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("badgeId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(badgeLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(Badge.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("badgeId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return badgeLocalService.deleteBadge((Badge)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return badgePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the badges matching the UUID and company.
	 *
	 * @param uuid the UUID of the badges
	 * @param companyId the primary key of the company
	 * @return the matching badges, or an empty list if no matches were found
	 */
	@Override
	public List<Badge> getBadgesByUuidAndCompanyId(String uuid, long companyId) {
		return badgePersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of badges matching the UUID and company.
	 *
	 * @param uuid the UUID of the badges
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of badges
	 * @param end the upper bound of the range of badges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching badges, or an empty list if no matches were found
	 */
	@Override
	public List<Badge> getBadgesByUuidAndCompanyId(String uuid, long companyId,
		int start, int end, OrderByComparator<Badge> orderByComparator) {
		return badgePersistence.findByUuid_C(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	 * Returns the badge matching the UUID and group.
	 *
	 * @param uuid the badge's UUID
	 * @param groupId the primary key of the group
	 * @return the matching badge
	 * @throws PortalException if a matching badge could not be found
	 */
	@Override
	public Badge getBadgeByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {
		return badgePersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the badges.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.grow.gamification.model.impl.BadgeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of badges
	 * @param end the upper bound of the range of badges (not inclusive)
	 * @return the range of badges
	 */
	@Override
	public List<Badge> getBadges(int start, int end) {
		return badgePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of badges.
	 *
	 * @return the number of badges
	 */
	@Override
	public int getBadgesCount() {
		return badgePersistence.countAll();
	}

	/**
	 * Updates the badge in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param badge the badge
	 * @return the badge that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Badge updateBadge(Badge badge) {
		return badgePersistence.update(badge);
	}

	/**
	 * Returns the badge local service.
	 *
	 * @return the badge local service
	 */
	public BadgeLocalService getBadgeLocalService() {
		return badgeLocalService;
	}

	/**
	 * Sets the badge local service.
	 *
	 * @param badgeLocalService the badge local service
	 */
	public void setBadgeLocalService(BadgeLocalService badgeLocalService) {
		this.badgeLocalService = badgeLocalService;
	}

	/**
	 * Returns the badge persistence.
	 *
	 * @return the badge persistence
	 */
	public BadgePersistence getBadgePersistence() {
		return badgePersistence;
	}

	/**
	 * Sets the badge persistence.
	 *
	 * @param badgePersistence the badge persistence
	 */
	public void setBadgePersistence(BadgePersistence badgePersistence) {
		this.badgePersistence = badgePersistence;
	}

	/**
	 * Returns the badge type local service.
	 *
	 * @return the badge type local service
	 */
	public com.liferay.grow.gamification.service.BadgeTypeLocalService getBadgeTypeLocalService() {
		return badgeTypeLocalService;
	}

	/**
	 * Sets the badge type local service.
	 *
	 * @param badgeTypeLocalService the badge type local service
	 */
	public void setBadgeTypeLocalService(
		com.liferay.grow.gamification.service.BadgeTypeLocalService badgeTypeLocalService) {
		this.badgeTypeLocalService = badgeTypeLocalService;
	}

	/**
	 * Returns the badge type persistence.
	 *
	 * @return the badge type persistence
	 */
	public BadgeTypePersistence getBadgeTypePersistence() {
		return badgeTypePersistence;
	}

	/**
	 * Sets the badge type persistence.
	 *
	 * @param badgeTypePersistence the badge type persistence
	 */
	public void setBadgeTypePersistence(
		BadgeTypePersistence badgeTypePersistence) {
		this.badgeTypePersistence = badgeTypePersistence;
	}

	/**
	 * Returns the l date local service.
	 *
	 * @return the l date local service
	 */
	public com.liferay.grow.gamification.service.LDateLocalService getLDateLocalService() {
		return lDateLocalService;
	}

	/**
	 * Sets the l date local service.
	 *
	 * @param lDateLocalService the l date local service
	 */
	public void setLDateLocalService(
		com.liferay.grow.gamification.service.LDateLocalService lDateLocalService) {
		this.lDateLocalService = lDateLocalService;
	}

	/**
	 * Returns the l date persistence.
	 *
	 * @return the l date persistence
	 */
	public LDatePersistence getLDatePersistence() {
		return lDatePersistence;
	}

	/**
	 * Sets the l date persistence.
	 *
	 * @param lDatePersistence the l date persistence
	 */
	public void setLDatePersistence(LDatePersistence lDatePersistence) {
		this.lDatePersistence = lDatePersistence;
	}

	/**
	 * Returns the subscriber local service.
	 *
	 * @return the subscriber local service
	 */
	public com.liferay.grow.gamification.service.SubscriberLocalService getSubscriberLocalService() {
		return subscriberLocalService;
	}

	/**
	 * Sets the subscriber local service.
	 *
	 * @param subscriberLocalService the subscriber local service
	 */
	public void setSubscriberLocalService(
		com.liferay.grow.gamification.service.SubscriberLocalService subscriberLocalService) {
		this.subscriberLocalService = subscriberLocalService;
	}

	/**
	 * Returns the subscriber persistence.
	 *
	 * @return the subscriber persistence
	 */
	public SubscriberPersistence getSubscriberPersistence() {
		return subscriberPersistence;
	}

	/**
	 * Sets the subscriber persistence.
	 *
	 * @param subscriberPersistence the subscriber persistence
	 */
	public void setSubscriberPersistence(
		SubscriberPersistence subscriberPersistence) {
		this.subscriberPersistence = subscriberPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.grow.gamification.model.Badge",
			badgeLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.grow.gamification.model.Badge");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return BadgeLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return Badge.class;
	}

	protected String getModelClassName() {
		return Badge.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = badgePersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = BadgeLocalService.class)
	protected BadgeLocalService badgeLocalService;
	@BeanReference(type = BadgePersistence.class)
	protected BadgePersistence badgePersistence;
	@BeanReference(type = com.liferay.grow.gamification.service.BadgeTypeLocalService.class)
	protected com.liferay.grow.gamification.service.BadgeTypeLocalService badgeTypeLocalService;
	@BeanReference(type = BadgeTypePersistence.class)
	protected BadgeTypePersistence badgeTypePersistence;
	@BeanReference(type = com.liferay.grow.gamification.service.LDateLocalService.class)
	protected com.liferay.grow.gamification.service.LDateLocalService lDateLocalService;
	@BeanReference(type = LDatePersistence.class)
	protected LDatePersistence lDatePersistence;
	@BeanReference(type = com.liferay.grow.gamification.service.SubscriberLocalService.class)
	protected com.liferay.grow.gamification.service.SubscriberLocalService subscriberLocalService;
	@BeanReference(type = SubscriberPersistence.class)
	protected SubscriberPersistence subscriberPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}