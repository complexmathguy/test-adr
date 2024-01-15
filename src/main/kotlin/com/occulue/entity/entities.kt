/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.entity;

import java.util.*

import javax.persistence.*
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery

import com.occulue.api.*;

// --------------------------------------------
// Entity Definitions
// --------------------------------------------
@Entity
data class Event(
    @Id var eventId: UUID? = null,
    var createdDateTime:  Date? = null,
    var modificationDateTime:  Date? = null,
    var eventName: String? = null,
    var priority: Int? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "program") var program: Program? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "targets") var targets: ValuesMap? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "reportDescriptors") var reportDescriptors:  Set<ReportDescriptor>? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "payloadDescriptors") var payloadDescriptors:  Set<PayloadDescriptor>? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "intervals") var intervals:  Set<Interval>? = null,
    @Enumerated(EnumType.STRING) var objectType: ObjectType? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "intervalPeriod") var intervalPeriod: IntervalPeriod? = null
)

@Entity
data class EventPayloadDescriptor(
    @Id var eventPayloadDescriptorId: UUID? = null,
    var payloadType: String? = null,
    var units: String? = null,
    var currency: String? = null,
    @Enumerated(EnumType.STRING) var objectType: ObjectType? = null
)

@Entity
data class Interval(
    @Id var intervalId: UUID? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "payloads") var payloads:  Set<ValuesMap>? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "intervalPeriod") var intervalPeriod: IntervalPeriod? = null
)

@Entity
data class IntervalPeriod(
    @Id var intervalPeriodId: UUID? = null,
    var start:  Date? = null,
     @Embedded     @AttributeOverrides(
    )
    var duration:  Duration? = null,
     @Embedded     @AttributeOverrides(
    )
    var randomizeStart:  Duration? = null
)

@Entity
data class Notification(
    @Id var notificationId: UUID? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "targets") var targets: ValuesMap? = null,
    @Enumerated(EnumType.STRING) var objectType: ObjectType? = null,
    @Enumerated(EnumType.STRING) var operation: Operation? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "notifier") var notifier: Notifier? = null
)

@Entity
data class Notifier(
     var notifierId: UUID? = null
)

@Entity
data class ObjectOperation(
    @Id var objectOperationId: UUID? = null,
    var callbackUrl: String? = null,
    var bearerToken: String? = null,
    @Enumerated(EnumType.STRING) var objects: ObjectType? = null,
    @Enumerated(EnumType.STRING) var operations: Operation? = null
)

@Entity
data class PayloadDescriptor(
     var payloadDescriptorId: UUID? = null
)

@Entity
data class Problem(
    @Id var problemId: UUID? = null,
    var type: String? = null,
    var title: String? = null,
    var status: Int? = null,
    var detail: String? = null,
    var instance: String? = null
)

@Entity
data class Program(
    @Id var programId: UUID? = null,
    var createdDateTime:  Date? = null,
    var modificationDateTime:  Date? = null,
    var programName: String? = null,
    var programLongName: String? = null,
    var retailerName: String? = null,
    var retailerLongName: String? = null,
    var programType: String? = null,
    var country: String? = null,
    var principalSubdivision: String? = null,
     @Embedded     @AttributeOverrides(
    )
    var timeZoneOffset:  Duration? = null,
    var programDescriptions:  ArrayList<String>? = null,
    var bindingEvents: Boolean? = null,
    var localPrice: Boolean? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "payloadDescriptors") var payloadDescriptors:  Set<PayloadDescriptor>? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "targets") var targets:  Set<ValuesMap>? = null,
    @Enumerated(EnumType.STRING) var objectType: ObjectType? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "intervalPeriod") var intervalPeriod: IntervalPeriod? = null
)

@Entity
data class Report(
    @Id var reportId: UUID? = null,
    var createdDateTime:  Date? = null,
    var modificationDateTime:  Date? = null,
    var clientName: String? = null,
    var reportName: String? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "program") var program: Program? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "event") var event: Event? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "payloadDescriptors") var payloadDescriptors:  Set<PayloadDescriptor>? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "resources") var resources:  Set<Resource>? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "intervals") var intervals: Interval? = null,
    @Enumerated(EnumType.STRING) var objectType: ObjectType? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "intervalPeriod") var intervalPeriod: IntervalPeriod? = null
)

@Entity
data class ReportDescriptor(
    @Id var reportDescriptorId: UUID? = null,
    var payloadType: String? = null,
    var readingType: String? = null,
    var units: String? = null,
    var aggregate: Boolean? = null,
    var startInterval: Int? = null,
    var numIntervals: Int? = null,
    var historical: Boolean? = null,
    var frequency: Int? = null,
    var repeat: Int? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "targets") var targets: ValuesMap? = null
)

@Entity
data class ReportPayloadDescriptor(
    @Id var reportPayloadDescriptorId: UUID? = null,
    var payloadType: String? = null,
    var readingType: String? = null,
    var units: String? = null,
    var accuracy: Float? = null,
    var confidence: Int? = null,
    @Enumerated(EnumType.STRING) var objectType: ObjectType? = null
)

@Entity
data class Resource(
    @Id var resourceId: UUID? = null,
    var createdDateTime:  Date? = null,
    var modificationDateTime:  Date? = null,
    var resourceName: String? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "ven") var ven: Ven? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "attributes") var attributes:  Set<ValuesMap>? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "targets") var targets:  Set<ValuesMap>? = null,
    @Enumerated(EnumType.STRING) var objectType: ObjectType? = null
)

@Entity
data class Subscription(
    @Id var subscriptionId: UUID? = null,
    var createdDateTime:  Date? = null,
    var modificationDateTime:  Date? = null,
    var clientName: String? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "program") var program: Program? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "objectOperations") var objectOperations:  Set<ObjectOperation>? = null,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "targets") var targets: ValuesMap? = null,
    @Enumerated(EnumType.STRING) var objectType: ObjectType? = null
)

@Entity
data class ValuesMap(
    @Id var valuesMapId: UUID? = null,
    var type: String? = null,
    var values:  ArrayList<String>? = null
)

@Entity
data class Ven(
    @Id var venId: UUID? = null,
    var createdDateTime:  Date? = null,
    var modificationDateTime:  Date? = null,
    var venName: String? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "attributes") var attributes:  Set<ValuesMap>? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "targets") var targets:  Set<ValuesMap>? = null,
    @OneToMany(fetch = FetchType.EAGER) @JoinColumn(name = "resources") var resources:  Set<Resource>? = null,
    @Enumerated(EnumType.STRING) var objectType: ObjectType? = null
)

