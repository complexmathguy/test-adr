import React, { Component } from 'react'
import EventService from '../services/EventService';

class CreateEventComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
                createdDateTime: '',
                modificationDateTime: '',
                eventName: '',
                priority: '',
                objectType: ''
        }
        this.changecreatedDateTimeHandler = this.changecreatedDateTimeHandler.bind(this);
        this.changemodificationDateTimeHandler = this.changemodificationDateTimeHandler.bind(this);
        this.changeeventNameHandler = this.changeeventNameHandler.bind(this);
        this.changepriorityHandler = this.changepriorityHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
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
    }
    saveOrUpdateEvent = (e) => {
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

        // step 5
        if(this.state.id === '_add'){
            event.eventId=''
            EventService.createEvent(event).then(res =>{
                this.props.history.push('/events');
            });
        }else{
            EventService.updateEvent(event).then( res => {
                this.props.history.push('/events');
            });
        }
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

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Event</h3>
        }else{
            return <h3 className="text-center">Update Event</h3>
        }
    }
    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.getTitle()
                                }
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> createdDateTime: </label>
                                            #formFields( $attribute, 'create')
                                            <label> modificationDateTime: </label>
                                            #formFields( $attribute, 'create')
                                            <label> eventName: </label>
                                            #formFields( $attribute, 'create')
                                            <label> priority: </label>
                                            #formFields( $attribute, 'create')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateEvent}>Save</button>
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

export default CreateEventComponent
