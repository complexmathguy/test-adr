import React, { Component } from 'react'
import EventService from '../services/EventService'

class ViewEventComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            event: {}
        }
    }

    componentDidMount(){
        EventService.getEventById(this.state.id).then( res => {
            this.setState({event: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Event Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> createdDateTime:&emsp; </label>
                            <div> { this.state.event.createdDateTime }</div>
                        </div>
                        <div className = "row">
                            <label> modificationDateTime:&emsp; </label>
                            <div> { this.state.event.modificationDateTime }</div>
                        </div>
                        <div className = "row">
                            <label> eventName:&emsp; </label>
                            <div> { this.state.event.eventName }</div>
                        </div>
                        <div className = "row">
                            <label> priority:&emsp; </label>
                            <div> { this.state.event.priority }</div>
                        </div>
                        <div className = "row">
                            <label> ObjectType:&emsp; </label>
                            <div> { this.state.event.objectType }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewEventComponent
