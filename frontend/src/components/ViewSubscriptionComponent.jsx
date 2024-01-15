import React, { Component } from 'react'
import SubscriptionService from '../services/SubscriptionService'

class ViewSubscriptionComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            subscription: {}
        }
    }

    componentDidMount(){
        SubscriptionService.getSubscriptionById(this.state.id).then( res => {
            this.setState({subscription: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Subscription Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> createdDateTime:&emsp; </label>
                            <div> { this.state.subscription.createdDateTime }</div>
                        </div>
                        <div className = "row">
                            <label> modificationDateTime:&emsp; </label>
                            <div> { this.state.subscription.modificationDateTime }</div>
                        </div>
                        <div className = "row">
                            <label> clientName:&emsp; </label>
                            <div> { this.state.subscription.clientName }</div>
                        </div>
                        <div className = "row">
                            <label> ObjectType:&emsp; </label>
                            <div> { this.state.subscription.objectType }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewSubscriptionComponent
