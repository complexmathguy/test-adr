import React, { Component } from 'react'
import SubscriptionService from '../services/SubscriptionService'

class ListSubscriptionComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                subscriptions: []
        }
        this.addSubscription = this.addSubscription.bind(this);
        this.editSubscription = this.editSubscription.bind(this);
        this.deleteSubscription = this.deleteSubscription.bind(this);
    }

    deleteSubscription(id){
        SubscriptionService.deleteSubscription(id).then( res => {
            this.setState({subscriptions: this.state.subscriptions.filter(subscription => subscription.subscriptionId !== id)});
        });
    }
    viewSubscription(id){
        this.props.history.push(`/view-subscription/${id}`);
    }
    editSubscription(id){
        this.props.history.push(`/add-subscription/${id}`);
    }

    componentDidMount(){
        SubscriptionService.getSubscriptions().then((res) => {
            this.setState({ subscriptions: res.data});
        });
    }

    addSubscription(){
        this.props.history.push('/add-subscription/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Subscription List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addSubscription}> Add Subscription</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> CreatedDateTime </th>
                                    <th> ModificationDateTime </th>
                                    <th> ClientName </th>
                                    <th> ObjectType </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.subscriptions.map(
                                        subscription => 
                                        <tr key = {subscription.subscriptionId}>
                                             <td> { subscription.createdDateTime } </td>
                                             <td> { subscription.modificationDateTime } </td>
                                             <td> { subscription.clientName } </td>
                                             <td> { subscription.objectType } </td>
                                             <td>
                                                 <button onClick={ () => this.editSubscription(subscription.subscriptionId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteSubscription(subscription.subscriptionId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewSubscription(subscription.subscriptionId)} className="btn btn-info btn-sm">View </button>
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

export default ListSubscriptionComponent
