import React, { Component } from 'react'
import EventPayloadDescriptorService from '../services/EventPayloadDescriptorService';

class UpdateEventPayloadDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                payloadType: '',
                units: '',
                currency: '',
                objectType: ''
        }
        this.updateEventPayloadDescriptor = this.updateEventPayloadDescriptor.bind(this);

        this.changepayloadTypeHandler = this.changepayloadTypeHandler.bind(this);
        this.changeunitsHandler = this.changeunitsHandler.bind(this);
        this.changecurrencyHandler = this.changecurrencyHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    componentDidMount(){
        EventPayloadDescriptorService.getEventPayloadDescriptorById(this.state.id).then( (res) =>{
            let eventPayloadDescriptor = res.data;
            this.setState({
                payloadType: eventPayloadDescriptor.payloadType,
                units: eventPayloadDescriptor.units,
                currency: eventPayloadDescriptor.currency,
                objectType: eventPayloadDescriptor.objectType
            });
        });
    }

    updateEventPayloadDescriptor = (e) => {
        e.preventDefault();
        let eventPayloadDescriptor = {
            eventPayloadDescriptorId: this.state.id,
            payloadType: this.state.payloadType,
            units: this.state.units,
            currency: this.state.currency,
            objectType: this.state.objectType
        };
        console.log('eventPayloadDescriptor => ' + JSON.stringify(eventPayloadDescriptor));
        console.log('id => ' + JSON.stringify(this.state.id));
        EventPayloadDescriptorService.updateEventPayloadDescriptor(eventPayloadDescriptor).then( res => {
            this.props.history.push('/eventPayloadDescriptors');
        });
    }

    changepayloadTypeHandler= (event) => {
        this.setState({payloadType: event.target.value});
    }
    changeunitsHandler= (event) => {
        this.setState({units: event.target.value});
    }
    changecurrencyHandler= (event) => {
        this.setState({currency: event.target.value});
    }
    changeObjectTypeHandler= (event) => {
        this.setState({objectType: event.target.value});
    }

    cancel(){
        this.props.history.push('/eventPayloadDescriptors');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update EventPayloadDescriptor</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> payloadType: </label>
                                            #formFields( $attribute, 'update')
                                            <label> units: </label>
                                            #formFields( $attribute, 'update')
                                            <label> currency: </label>
                                            #formFields( $attribute, 'update')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateEventPayloadDescriptor}>Save</button>
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

export default UpdateEventPayloadDescriptorComponent
