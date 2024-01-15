import React, { Component } from 'react'
import EventService from '../services/EventService'

class ListEventComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                events: []
        }
        this.addEvent = this.addEvent.bind(this);
        this.editEvent = this.editEvent.bind(this);
        this.deleteEvent = this.deleteEvent.bind(this);
    }

    deleteEvent(id){
        EventService.deleteEvent(id).then( res => {
            this.setState({events: this.state.events.filter(event => event.eventId !== id)});
        });
    }
    viewEvent(id){
        this.props.history.push(`/view-event/${id}`);
    }
    editEvent(id){
        this.props.history.push(`/add-event/${id}`);
    }

    componentDidMount(){
        EventService.getEvents().then((res) => {
            this.setState({ events: res.data});
        });
    }

    addEvent(){
        this.props.history.push('/add-event/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Event List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addEvent}> Add Event</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> CreatedDateTime </th>
                                    <th> ModificationDateTime </th>
                                    <th> EventName </th>
                                    <th> Priority </th>
                                    <th> ObjectType </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.events.map(
                                        event => 
                                        <tr key = {event.eventId}>
                                             <td> { event.createdDateTime } </td>
                                             <td> { event.modificationDateTime } </td>
                                             <td> { event.eventName } </td>
                                             <td> { event.priority } </td>
                                             <td> { event.objectType } </td>
                                             <td>
                                                 <button onClick={ () => this.editEvent(event.eventId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteEvent(event.eventId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewEvent(event.eventId)} className="btn btn-info btn-sm">View </button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    }
}

export default ListEventComponent
