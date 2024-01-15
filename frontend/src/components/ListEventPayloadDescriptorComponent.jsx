import React, { Component } from 'react'
import EventPayloadDescriptorService from '../services/EventPayloadDescriptorService'

class ListEventPayloadDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                eventPayloadDescriptors: []
        }
        this.addEventPayloadDescriptor = this.addEventPayloadDescriptor.bind(this);
        this.editEventPayloadDescriptor = this.editEventPayloadDescriptor.bind(this);
        this.deleteEventPayloadDescriptor = this.deleteEventPayloadDescriptor.bind(this);
    }

    deleteEventPayloadDescriptor(id){
        EventPayloadDescriptorService.deleteEventPayloadDescriptor(id).then( res => {
            this.setState({eventPayloadDescriptors: this.state.eventPayloadDescriptors.filter(eventPayloadDescriptor => eventPayloadDescriptor.eventPayloadDescriptorId !== id)});
        });
    }
    viewEventPayloadDescriptor(id){
        this.props.history.push(`/view-eventPayloadDescriptor/${id}`);
    }
    editEventPayloadDescriptor(id){
        this.props.history.push(`/add-eventPayloadDescriptor/${id}`);
    }

    componentDidMount(){
        EventPayloadDescriptorService.getEventPayloadDescriptors().then((res) => {
            this.setState({ eventPayloadDescriptors: res.data});
        });
    }

    addEventPayloadDescriptor(){
        this.props.history.push('/add-eventPayloadDescriptor/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">EventPayloadDescriptor List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addEventPayloadDescriptor}> Add EventPayloadDescriptor</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> PayloadType </th>
                                    <th> Units </th>
                                    <th> Currency </th>
                                    <th> ObjectType </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.eventPayloadDescriptors.map(
                                        eventPayloadDescriptor => 
                                        <tr key = {eventPayloadDescriptor.eventPayloadDescriptorId}>
                                             <td> { eventPayloadDescriptor.payloadType } </td>
                                             <td> { eventPayloadDescriptor.units } </td>
                                             <td> { eventPayloadDescriptor.currency } </td>
                                             <td> { eventPayloadDescriptor.objectType } </td>
                                             <td>
                                                 <button onClick={ () => this.editEventPayloadDescriptor(eventPayloadDescriptor.eventPayloadDescriptorId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteEventPayloadDescriptor(eventPayloadDescriptor.eventPayloadDescriptorId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewEventPayloadDescriptor(eventPayloadDescriptor.eventPayloadDescriptorId)} className="btn btn-info btn-sm">View </button>
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

export default ListEventPayloadDescriptorComponent
