import React from 'react';
import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import HomePageComponent from './components/HomePageComponent';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import ListEventComponent from './components/ListEventComponent';
import CreateEventComponent from './components/CreateEventComponent';
import UpdateEventComponent from './components/UpdateEventComponent';
import ViewEventComponent from './components/ViewEventComponent';
import ListEventPayloadDescriptorComponent from './components/ListEventPayloadDescriptorComponent';
import CreateEventPayloadDescriptorComponent from './components/CreateEventPayloadDescriptorComponent';
import UpdateEventPayloadDescriptorComponent from './components/UpdateEventPayloadDescriptorComponent';
import ViewEventPayloadDescriptorComponent from './components/ViewEventPayloadDescriptorComponent';
import ListIntervalComponent from './components/ListIntervalComponent';
import CreateIntervalComponent from './components/CreateIntervalComponent';
import UpdateIntervalComponent from './components/UpdateIntervalComponent';
import ViewIntervalComponent from './components/ViewIntervalComponent';
import ListIntervalPeriodComponent from './components/ListIntervalPeriodComponent';
import CreateIntervalPeriodComponent from './components/CreateIntervalPeriodComponent';
import UpdateIntervalPeriodComponent from './components/UpdateIntervalPeriodComponent';
import ViewIntervalPeriodComponent from './components/ViewIntervalPeriodComponent';
import ListNotificationComponent from './components/ListNotificationComponent';
import CreateNotificationComponent from './components/CreateNotificationComponent';
import UpdateNotificationComponent from './components/UpdateNotificationComponent';
import ViewNotificationComponent from './components/ViewNotificationComponent';
import ListObjectOperationComponent from './components/ListObjectOperationComponent';
import CreateObjectOperationComponent from './components/CreateObjectOperationComponent';
import UpdateObjectOperationComponent from './components/UpdateObjectOperationComponent';
import ViewObjectOperationComponent from './components/ViewObjectOperationComponent';
import ListProblemComponent from './components/ListProblemComponent';
import CreateProblemComponent from './components/CreateProblemComponent';
import UpdateProblemComponent from './components/UpdateProblemComponent';
import ViewProblemComponent from './components/ViewProblemComponent';
import ListProgramComponent from './components/ListProgramComponent';
import CreateProgramComponent from './components/CreateProgramComponent';
import UpdateProgramComponent from './components/UpdateProgramComponent';
import ViewProgramComponent from './components/ViewProgramComponent';
import ListReportComponent from './components/ListReportComponent';
import CreateReportComponent from './components/CreateReportComponent';
import UpdateReportComponent from './components/UpdateReportComponent';
import ViewReportComponent from './components/ViewReportComponent';
import ListReportDescriptorComponent from './components/ListReportDescriptorComponent';
import CreateReportDescriptorComponent from './components/CreateReportDescriptorComponent';
import UpdateReportDescriptorComponent from './components/UpdateReportDescriptorComponent';
import ViewReportDescriptorComponent from './components/ViewReportDescriptorComponent';
import ListReportPayloadDescriptorComponent from './components/ListReportPayloadDescriptorComponent';
import CreateReportPayloadDescriptorComponent from './components/CreateReportPayloadDescriptorComponent';
import UpdateReportPayloadDescriptorComponent from './components/UpdateReportPayloadDescriptorComponent';
import ViewReportPayloadDescriptorComponent from './components/ViewReportPayloadDescriptorComponent';
import ListResourceComponent from './components/ListResourceComponent';
import CreateResourceComponent from './components/CreateResourceComponent';
import UpdateResourceComponent from './components/UpdateResourceComponent';
import ViewResourceComponent from './components/ViewResourceComponent';
import ListSubscriptionComponent from './components/ListSubscriptionComponent';
import CreateSubscriptionComponent from './components/CreateSubscriptionComponent';
import UpdateSubscriptionComponent from './components/UpdateSubscriptionComponent';
import ViewSubscriptionComponent from './components/ViewSubscriptionComponent';
import ListValuesMapComponent from './components/ListValuesMapComponent';
import CreateValuesMapComponent from './components/CreateValuesMapComponent';
import UpdateValuesMapComponent from './components/UpdateValuesMapComponent';
import ViewValuesMapComponent from './components/ViewValuesMapComponent';
import ListVenComponent from './components/ListVenComponent';
import CreateVenComponent from './components/CreateVenComponent';
import UpdateVenComponent from './components/UpdateVenComponent';
import ViewVenComponent from './components/ViewVenComponent';
#outputExtraInclusionComponents()
function App() {
  return (
    <div>
        <Router>
                <HeaderComponent className="header"/>
                <div className="container">
                    <Switch>
                          <Route path = "/" exact component = {HomePageComponent}></Route>
                            <Route path = "/events" component = {ListEventComponent}></Route>
                            <Route path = "/add-event/:id" component = {CreateEventComponent}></Route>
                            <Route path = "/view-event/:id" component = {ViewEventComponent}></Route>
                          {/* <Route path = "/update-event/:id" component = {UpdateEventComponent}></Route> */}
                            <Route path = "/eventPayloadDescriptors" component = {ListEventPayloadDescriptorComponent}></Route>
                            <Route path = "/add-eventPayloadDescriptor/:id" component = {CreateEventPayloadDescriptorComponent}></Route>
                            <Route path = "/view-eventPayloadDescriptor/:id" component = {ViewEventPayloadDescriptorComponent}></Route>
                          {/* <Route path = "/update-eventPayloadDescriptor/:id" component = {UpdateEventPayloadDescriptorComponent}></Route> */}
                            <Route path = "/intervals" component = {ListIntervalComponent}></Route>
                            <Route path = "/add-interval/:id" component = {CreateIntervalComponent}></Route>
                            <Route path = "/view-interval/:id" component = {ViewIntervalComponent}></Route>
                          {/* <Route path = "/update-interval/:id" component = {UpdateIntervalComponent}></Route> */}
                            <Route path = "/intervalPeriods" component = {ListIntervalPeriodComponent}></Route>
                            <Route path = "/add-intervalPeriod/:id" component = {CreateIntervalPeriodComponent}></Route>
                            <Route path = "/view-intervalPeriod/:id" component = {ViewIntervalPeriodComponent}></Route>
                          {/* <Route path = "/update-intervalPeriod/:id" component = {UpdateIntervalPeriodComponent}></Route> */}
                            <Route path = "/notifications" component = {ListNotificationComponent}></Route>
                            <Route path = "/add-notification/:id" component = {CreateNotificationComponent}></Route>
                            <Route path = "/view-notification/:id" component = {ViewNotificationComponent}></Route>
                          {/* <Route path = "/update-notification/:id" component = {UpdateNotificationComponent}></Route> */}
                            <Route path = "/objectOperations" component = {ListObjectOperationComponent}></Route>
                            <Route path = "/add-objectOperation/:id" component = {CreateObjectOperationComponent}></Route>
                            <Route path = "/view-objectOperation/:id" component = {ViewObjectOperationComponent}></Route>
                          {/* <Route path = "/update-objectOperation/:id" component = {UpdateObjectOperationComponent}></Route> */}
                            <Route path = "/problems" component = {ListProblemComponent}></Route>
                            <Route path = "/add-problem/:id" component = {CreateProblemComponent}></Route>
                            <Route path = "/view-problem/:id" component = {ViewProblemComponent}></Route>
                          {/* <Route path = "/update-problem/:id" component = {UpdateProblemComponent}></Route> */}
                            <Route path = "/programs" component = {ListProgramComponent}></Route>
                            <Route path = "/add-program/:id" component = {CreateProgramComponent}></Route>
                            <Route path = "/view-program/:id" component = {ViewProgramComponent}></Route>
                          {/* <Route path = "/update-program/:id" component = {UpdateProgramComponent}></Route> */}
                            <Route path = "/reports" component = {ListReportComponent}></Route>
                            <Route path = "/add-report/:id" component = {CreateReportComponent}></Route>
                            <Route path = "/view-report/:id" component = {ViewReportComponent}></Route>
                          {/* <Route path = "/update-report/:id" component = {UpdateReportComponent}></Route> */}
                            <Route path = "/reportDescriptors" component = {ListReportDescriptorComponent}></Route>
                            <Route path = "/add-reportDescriptor/:id" component = {CreateReportDescriptorComponent}></Route>
                            <Route path = "/view-reportDescriptor/:id" component = {ViewReportDescriptorComponent}></Route>
                          {/* <Route path = "/update-reportDescriptor/:id" component = {UpdateReportDescriptorComponent}></Route> */}
                            <Route path = "/reportPayloadDescriptors" component = {ListReportPayloadDescriptorComponent}></Route>
                            <Route path = "/add-reportPayloadDescriptor/:id" component = {CreateReportPayloadDescriptorComponent}></Route>
                            <Route path = "/view-reportPayloadDescriptor/:id" component = {ViewReportPayloadDescriptorComponent}></Route>
                          {/* <Route path = "/update-reportPayloadDescriptor/:id" component = {UpdateReportPayloadDescriptorComponent}></Route> */}
                            <Route path = "/resources" component = {ListResourceComponent}></Route>
                            <Route path = "/add-resource/:id" component = {CreateResourceComponent}></Route>
                            <Route path = "/view-resource/:id" component = {ViewResourceComponent}></Route>
                          {/* <Route path = "/update-resource/:id" component = {UpdateResourceComponent}></Route> */}
                            <Route path = "/subscriptions" component = {ListSubscriptionComponent}></Route>
                            <Route path = "/add-subscription/:id" component = {CreateSubscriptionComponent}></Route>
                            <Route path = "/view-subscription/:id" component = {ViewSubscriptionComponent}></Route>
                          {/* <Route path = "/update-subscription/:id" component = {UpdateSubscriptionComponent}></Route> */}
                            <Route path = "/valuesMaps" component = {ListValuesMapComponent}></Route>
                            <Route path = "/add-valuesMap/:id" component = {CreateValuesMapComponent}></Route>
                            <Route path = "/view-valuesMap/:id" component = {ViewValuesMapComponent}></Route>
                          {/* <Route path = "/update-valuesMap/:id" component = {UpdateValuesMapComponent}></Route> */}
                            <Route path = "/vens" component = {ListVenComponent}></Route>
                            <Route path = "/add-ven/:id" component = {CreateVenComponent}></Route>
                            <Route path = "/view-ven/:id" component = {ViewVenComponent}></Route>
                          {/* <Route path = "/update-ven/:id" component = {UpdateVenComponent}></Route> */}
#outputExtraRoutePaths()
                    </Switch>
                </div>
              <FooterComponent />
        </Router>
    </div>
    
  );
}

export default App;
