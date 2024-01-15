import React, { Component } from 'react'
import EventService from '../services/EventService';

class UpdateEventComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                createdDateTime: '',
                modificationDateTime: '',
                eventName: '',
                priority: '',
                objectType: ''
        }
        this.updateEvent = this.updateEvent.bind(this);

        this.changecreatedDateTimeHandler = this.changecreatedDateTimeHandler.bind(this);
        this.changemodificationDateTimeHandler = this.changemodificationDateTimeHandler.bind(this);
        this.changeeventNameHandler = this.changeeventNameHandler.bind(this);
        this.changepriorityHandler = this.changepriorityHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    componentDidMount(){
        EventService.getEventById(this.state.id).then( (res) =>{
            let event = res.data;
            this.setState({
                createdDateTime: event.createdDateTime,
                modificationDateTime: event.modificationDateTime,
                eventName: event.eventName,
                priority: event.priority,
                objectType: event.objectType
            });
        });
    }

    updateEvent = (e) => {
        e.preventDefault();
        let event = {
            eventId: this.state.id,
            createdDateTime: this.state.createdDateTime,
            modificationDateTime: this.state.modificationDateTime,
            eventName: this.state.eventName,
            priority: this.state.priority,
            objectType: this.state.objectType
        };
        console.log('event => ' + JSON.stringify(event));
        console.log('id => ' + JSON.stringify(this.state.id));
        EventService.updateEvent(event).then( res => {
            this.props.history.push('/events');
        });
    }

    changecreatedDateTimeHandler= (event) => {
        this.setState({createdDateTime: event.target.value});
    }
    changemodificationDateTimeHandler= (event) => {
        this.setState({modificationDateTime: event.target.value});
    }
    changeeventNameHandler= (event) => {
        this.setState({eventName: event.target.value});
    }
    changepriorityHandler= (event) => {
        this.setState({priority: event.target.value});
    }
    changeObjectTypeHandler= (event) => {
        this.setState({objectType: event.target.value});
    }

    cancel(){
        this.props.history.push('/events');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update Event</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> createdDateTime: </label>
                                            #formFields( $attribute, 'update')
                                            <label> modificationDateTime: </label>
                                            #formFields( $attribute, 'update')
                                            <label> eventName: </label>
                                            #formFields( $attribute, 'update')
                                            <label> priority: </label>
                                            #formFields( $attribute, 'update')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateEvent}>Save</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                   </div>
            </div>
        )
    }
}

export default UpdateEventComponent
