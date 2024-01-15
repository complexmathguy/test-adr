import React, { Component } from 'react'
import NotificationService from '../services/NotificationService';

class CreateNotificationComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
                objectType: '',
                operation: ''
        }
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
        this.changeOperationHandler = this.changeOperationHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            NotificationService.getNotificationById(this.state.id).then( (res) =>{
                let notification = res.data;
                this.setState({
                    objectType: notification.objectType,
                    operation: notification.operation
                });
            });
        }        
    }
    saveOrUpdateNotification = (e) => {
        e.preventDefault();
        let notification = {
                notificationId: this.state.id,
                objectType: this.state.objectType,
                operation: this.state.operation
            };
        console.log('notification => ' + JSON.stringify(notification));

        // step 5
        if(this.state.id === '_add'){
            notification.notificationId=''
            NotificationService.createNotification(notification).then(res =>{
                this.props.history.push('/notifications');
            });
        }else{
            NotificationService.updateNotification(notification).then( res => {
                this.props.history.push('/notifications');
            });
        }
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

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Notification</h3>
        }else{
            return <h3 className="text-center">Update Notification</h3>
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
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'create')
                                            <label> Operation: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateNotification}>Save</button>
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

export default CreateNotificationComponent
