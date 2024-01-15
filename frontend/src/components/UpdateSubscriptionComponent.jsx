import React, { Component } from 'react'
import SubscriptionService from '../services/SubscriptionService';

class UpdateSubscriptionComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                createdDateTime: '',
                modificationDateTime: '',
                clientName: '',
                objectType: ''
        }
        this.updateSubscription = this.updateSubscription.bind(this);

        this.changecreatedDateTimeHandler = this.changecreatedDateTimeHandler.bind(this);
        this.changemodificationDateTimeHandler = this.changemodificationDateTimeHandler.bind(this);
        this.changeclientNameHandler = this.changeclientNameHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    componentDidMount(){
        SubscriptionService.getSubscriptionById(this.state.id).then( (res) =>{
            let subscription = res.data;
            this.setState({
                createdDateTime: subscription.createdDateTime,
                modificationDateTime: subscription.modificationDateTime,
                clientName: subscription.clientName,
                objectType: subscription.objectType
            });
        });
    }

    updateSubscription = (e) => {
        e.preventDefault();
        let subscription = {
            subscriptionId: this.state.id,
            createdDateTime: this.state.createdDateTime,
            modificationDateTime: this.state.modificationDateTime,
            clientName: this.state.clientName,
            objectType: this.state.objectType
        };
        console.log('subscription => ' + JSON.stringify(subscription));
        console.log('id => ' + JSON.stringify(this.state.id));
        SubscriptionService.updateSubscription(subscription).then( res => {
            this.props.history.push('/subscriptions');
        });
    }

    changecreatedDateTimeHandler= (event) => {
        this.setState({createdDateTime: event.target.value});
    }
    changemodificationDateTimeHandler= (event) => {
        this.setState({modificationDateTime: event.target.value});
    }
    changeclientNameHandler= (event) => {
        this.setState({clientName: event.target.value});
    }
    changeObjectTypeHandler= (event) => {
        this.setState({objectType: event.target.value});
    }

    cancel(){
        this.props.history.push('/subscriptions');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update Subscription</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> createdDateTime: </label>
                                            #formFields( $attribute, 'update')
                                            <label> modificationDateTime: </label>
                                            #formFields( $attribute, 'update')
                                            <label> clientName: </label>
                                            #formFields( $attribute, 'update')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateSubscription}>Save</button>
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

export default UpdateSubscriptionComponent
