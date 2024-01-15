/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.api;

import java.util.*;

import javax.persistence.Entity
import javax.persistence.Id

//-----------------------------------------------------------
// Query definitions
//-----------------------------------------------------------

// -----------------------------------------
// Event Queries 
// -----------------------------------------

data class LoadEventFilter(val eventId :  UUID? = null )

class FindEventQuery(val filter: LoadEventFilter = LoadEventFilter()) {
    override fun toString(): String = "LoadEventQuery"
}

class FindAllEventQuery() {
    override fun toString(): String = "LoadAllEventQuery"
}

data class EventFetchOneSummary(@Id var eventId : UUID? = null) {
}





// -----------------------------------------
// EventPayloadDescriptor Queries 
// -----------------------------------------

data class LoadEventPayloadDescriptorFilter(val eventPayloadDescriptorId :  UUID? = null )

class FindEventPayloadDescriptorQuery(val filter: LoadEventPayloadDescriptorFilter = LoadEventPayloadDescriptorFilter()) {
    override fun toString(): String = "LoadEventPayloadDescriptorQuery"
}

class FindAllEventPayloadDescriptorQuery() {
    override fun toString(): String = "LoadAllEventPayloadDescriptorQuery"
}

data class EventPayloadDescriptorFetchOneSummary(@Id var eventPayloadDescriptorId : UUID? = null) {
}





// -----------------------------------------
// Interval Queries 
// -----------------------------------------

data class LoadIntervalFilter(val intervalId :  UUID? = null )

class FindIntervalQuery(val filter: LoadIntervalFilter = LoadIntervalFilter()) {
    override fun toString(): String = "LoadIntervalQuery"
}

class FindAllIntervalQuery() {
    override fun toString(): String = "LoadAllIntervalQuery"
}

data class IntervalFetchOneSummary(@Id var intervalId : UUID? = null) {
}





// -----------------------------------------
// IntervalPeriod Queries 
// -----------------------------------------

data class LoadIntervalPeriodFilter(val intervalPeriodId :  UUID? = null )

class FindIntervalPeriodQuery(val filter: LoadIntervalPeriodFilter = LoadIntervalPeriodFilter()) {
    override fun toString(): String = "LoadIntervalPeriodQuery"
}

class FindAllIntervalPeriodQuery() {
    override fun toString(): String = "LoadAllIntervalPeriodQuery"
}

data class IntervalPeriodFetchOneSummary(@Id var intervalPeriodId : UUID? = null) {
}





// -----------------------------------------
// Notification Queries 
// -----------------------------------------

data class LoadNotificationFilter(val notificationId :  UUID? = null )

class FindNotificationQuery(val filter: LoadNotificationFilter = LoadNotificationFilter()) {
    override fun toString(): String = "LoadNotificationQuery"
}

class FindAllNotificationQuery() {
    override fun toString(): String = "LoadAllNotificationQuery"
}

data class NotificationFetchOneSummary(@Id var notificationId : UUID? = null) {
}





// -----------------------------------------
// Notifier Queries 
// -----------------------------------------

data class LoadNotifierFilter(val notifierId :  UUID? = null )

class FindNotifierQuery(val filter: LoadNotifierFilter = LoadNotifierFilter()) {
    override fun toString(): String = "LoadNotifierQuery"
}

class FindAllNotifierQuery() {
    override fun toString(): String = "LoadAllNotifierQuery"
}

data class NotifierFetchOneSummary(@Id var notifierId : UUID? = null) {
}





// -----------------------------------------
// ObjectOperation Queries 
// -----------------------------------------

data class LoadObjectOperationFilter(val objectOperationId :  UUID? = null )

class FindObjectOperationQuery(val filter: LoadObjectOperationFilter = LoadObjectOperationFilter()) {
    override fun toString(): String = "LoadObjectOperationQuery"
}

class FindAllObjectOperationQuery() {
    override fun toString(): String = "LoadAllObjectOperationQuery"
}

data class ObjectOperationFetchOneSummary(@Id var objectOperationId : UUID? = null) {
}





// -----------------------------------------
// PayloadDescriptor Queries 
// -----------------------------------------

data class LoadPayloadDescriptorFilter(val payloadDescriptorId :  UUID? = null )

class FindPayloadDescriptorQuery(val filter: LoadPayloadDescriptorFilter = LoadPayloadDescriptorFilter()) {
    override fun toString(): String = "LoadPayloadDescriptorQuery"
}

class FindAllPayloadDescriptorQuery() {
    override fun toString(): String = "LoadAllPayloadDescriptorQuery"
}

data class PayloadDescriptorFetchOneSummary(@Id var payloadDescriptorId : UUID? = null) {
}





// -----------------------------------------
// Problem Queries 
// -----------------------------------------

data class LoadProblemFilter(val problemId :  UUID? = null )

class FindProblemQuery(val filter: LoadProblemFilter = LoadProblemFilter()) {
    override fun toString(): String = "LoadProblemQuery"
}

class FindAllProblemQuery() {
    override fun toString(): String = "LoadAllProblemQuery"
}

data class ProblemFetchOneSummary(@Id var problemId : UUID? = null) {
}





// -----------------------------------------
// Program Queries 
// -----------------------------------------

data class LoadProgramFilter(val programId :  UUID? = null )

class FindProgramQuery(val filter: LoadProgramFilter = LoadProgramFilter()) {
    override fun toString(): String = "LoadProgramQuery"
}

class FindAllProgramQuery() {
    override fun toString(): String = "LoadAllProgramQuery"
}

data class ProgramFetchOneSummary(@Id var programId : UUID? = null) {
}





// -----------------------------------------
// Report Queries 
// -----------------------------------------

data class LoadReportFilter(val reportId :  UUID? = null )

class FindReportQuery(val filter: LoadReportFilter = LoadReportFilter()) {
    override fun toString(): String = "LoadReportQuery"
}

class FindAllReportQuery() {
    override fun toString(): String = "LoadAllReportQuery"
}

data class ReportFetchOneSummary(@Id var reportId : UUID? = null) {
}





// -----------------------------------------
// ReportDescriptor Queries 
// -----------------------------------------

data class LoadReportDescriptorFilter(val reportDescriptorId :  UUID? = null )

class FindReportDescriptorQuery(val filter: LoadReportDescriptorFilter = LoadReportDescriptorFilter()) {
    override fun toString(): String = "LoadReportDescriptorQuery"
}

class FindAllReportDescriptorQuery() {
    override fun toString(): String = "LoadAllReportDescriptorQuery"
}

data class ReportDescriptorFetchOneSummary(@Id var reportDescriptorId : UUID? = null) {
}





// -----------------------------------------
// ReportPayloadDescriptor Queries 
// -----------------------------------------

data class LoadReportPayloadDescriptorFilter(val reportPayloadDescriptorId :  UUID? = null )

class FindReportPayloadDescriptorQuery(val filter: LoadReportPayloadDescriptorFilter = LoadReportPayloadDescriptorFilter()) {
    override fun toString(): String = "LoadReportPayloadDescriptorQuery"
}

class FindAllReportPayloadDescriptorQuery() {
    override fun toString(): String = "LoadAllReportPayloadDescriptorQuery"
}

data class ReportPayloadDescriptorFetchOneSummary(@Id var reportPayloadDescriptorId : UUID? = null) {
}





// -----------------------------------------
// Resource Queries 
// -----------------------------------------

data class LoadResourceFilter(val resourceId :  UUID? = null )

class FindResourceQuery(val filter: LoadResourceFilter = LoadResourceFilter()) {
    override fun toString(): String = "LoadResourceQuery"
}

class FindAllResourceQuery() {
    override fun toString(): String = "LoadAllResourceQuery"
}

data class ResourceFetchOneSummary(@Id var resourceId : UUID? = null) {
}





// -----------------------------------------
// Subscription Queries 
// -----------------------------------------

data class LoadSubscriptionFilter(val subscriptionId :  UUID? = null )

class FindSubscriptionQuery(val filter: LoadSubscriptionFilter = LoadSubscriptionFilter()) {
    override fun toString(): String = "LoadSubscriptionQuery"
}

class FindAllSubscriptionQuery() {
    override fun toString(): String = "LoadAllSubscriptionQuery"
}

data class SubscriptionFetchOneSummary(@Id var subscriptionId : UUID? = null) {
}





// -----------------------------------------
// ValuesMap Queries 
// -----------------------------------------

data class LoadValuesMapFilter(val valuesMapId :  UUID? = null )

class FindValuesMapQuery(val filter: LoadValuesMapFilter = LoadValuesMapFilter()) {
    override fun toString(): String = "LoadValuesMapQuery"
}

class FindAllValuesMapQuery() {
    override fun toString(): String = "LoadAllValuesMapQuery"
}

data class ValuesMapFetchOneSummary(@Id var valuesMapId : UUID? = null) {
}





// -----------------------------------------
// Ven Queries 
// -----------------------------------------

data class LoadVenFilter(val venId :  UUID? = null )

class FindVenQuery(val filter: LoadVenFilter = LoadVenFilter()) {
    override fun toString(): String = "LoadVenQuery"
}

class FindAllVenQuery() {
    override fun toString(): String = "LoadAllVenQuery"
}

data class VenFetchOneSummary(@Id var venId : UUID? = null) {
}






