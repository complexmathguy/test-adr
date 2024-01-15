import React, { Component } from 'react'
import SubscriptionService from '../services/SubscriptionService';

class CreateSubscriptionComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
                createdDateTime: '',
                modificationDateTime: '',
                clientName: '',
                objectType: ''
        }
        this.changecreatedDateTimeHandler = this.changecreatedDateTimeHandler.bind(this);
        this.changemodificationDateTimeHandler = this.changemodificationDateTimeHandler.bind(this);
        this.changeclientNameHandler = this.changeclientNameHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
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
    }
    saveOrUpdateSubscription = (e) => {
        e.preventDefault();
        let subscription = {
                subscriptionId: this.state.id,
                createdDateTime: this.state.createdDateTime,
                modificationDateTime: this.state.modificationDateTime,
                clientName: this.state.clientName,
                objectType: this.state.objectType
            };
        console.log('subscription => ' + JSON.stringify(subscription));

        // step 5
        if(this.state.id === '_add'){
            subscription.subscriptionId=''
            SubscriptionService.createSubscription(subscription).then(res =>{
                this.props.history.push('/subscriptions');
            });
        }else{
            SubscriptionService.updateSubscription(subscription).then( res => {
                this.props.history.push('/subscriptions');
            });
        }
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

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Subscription</h3>
        }else{
            return <h3 className="text-center">Update Subscription</h3>
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
                                            <label> clientName: </label>
                                            #formFields( $attribute, 'create')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateSubscription}>Save</button>
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

export default CreateSubscriptionComponent
