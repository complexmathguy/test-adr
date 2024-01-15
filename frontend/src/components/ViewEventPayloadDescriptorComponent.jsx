import React, { Component } from 'react'
import EventPayloadDescriptorService from '../services/EventPayloadDescriptorService'

class ViewEventPayloadDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            eventPayloadDescriptor: {}
        }
    }

    componentDidMount(){
        EventPayloadDescriptorService.getEventPayloadDescriptorById(this.state.id).then( res => {
            this.setState({eventPayloadDescriptor: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View EventPayloadDescriptor Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> payloadType:&emsp; </label>
                            <div> { this.state.eventPayloadDescriptor.payloadType }</div>
                        </div>
                        <div className = "row">
                            <label> units:&emsp; </label>
                            <div> { this.state.eventPayloadDescriptor.units }</div>
                        </div>
                        <div className = "row">
                            <label> currency:&emsp; </label>
                            <div> { this.state.eventPayloadDescriptor.currency }</div>
                        </div>
                        <div className = "row">
                            <label> ObjectType:&emsp; </label>
                            <div> { this.state.eventPayloadDescriptor.objectType }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewEventPayloadDescriptorComponent
