import React, { Component } from 'react'
import NotificationService from '../services/NotificationService';

class UpdateNotificationComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                objectType: '',
                operation: ''
        }
        this.updateNotification = this.updateNotification.bind(this);

        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
        this.changeOperationHandler = this.changeOperationHandler.bind(this);
    }

    componentDidMount(){
        NotificationService.getNotificationById(this.state.id).then( (res) =>{
            let notification = res.data;
            this.setState({
                objectType: notification.objectType,
                operation: notification.operation
            });
        });
    }

    updateNotification = (e) => {
        e.preventDefault();
        let notification = {
            notificationId: this.state.id,
            objectType: this.state.objectType,
            operation: this.state.operation
        };
        console.log('notification => ' + JSON.stringify(notification));
        console.log('id => ' + JSON.stringify(this.state.id));
        NotificationService.updateNotification(notification).then( res => {
            this.props.history.push('/notifications');
        });
    }

    changeObjectTypeHandler= (event) => {
        this.setState({objectType: event.target.value});
    }
    changeOperationHandler= (event) => {
        this.setState({operation: event.target.value});
    }

    cancel(){
        this.props.history.push('/notifications');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update Notification</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'update')
                                            <label> Operation: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateNotification}>Save</button>
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

export default UpdateNotificationComponent
