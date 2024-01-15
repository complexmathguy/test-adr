import React, { Component } from 'react'
import NotificationService from '../services/NotificationService'

class ListNotificationComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                notifications: []
        }
        this.addNotification = this.addNotification.bind(this);
        this.editNotification = this.editNotification.bind(this);
        this.deleteNotification = this.deleteNotification.bind(this);
    }

    deleteNotification(id){
        NotificationService.deleteNotification(id).then( res => {
            this.setState({notifications: this.state.notifications.filter(notification => notification.notificationId !== id)});
        });
    }
    viewNotification(id){
        this.props.history.push(`/view-notification/${id}`);
    }
    editNotification(id){
        this.props.history.push(`/add-notification/${id}`);
    }

    componentDidMount(){
        NotificationService.getNotifications().then((res) => {
            this.setState({ notifications: res.data});
        });
    }

    addNotification(){
        this.props.history.push('/add-notification/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Notification List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addNotification}> Add Notification</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> ObjectType </th>
                                    <th> Operation </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.notifications.map(
                                        notification => 
                                        <tr key = {notification.notificationId}>
                                             <td> { notification.objectType } </td>
                                             <td> { notification.operation } </td>
                                             <td>
                                                 <button onClick={ () => this.editNotification(notification.notificationId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteNotification(notification.notificationId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewNotification(notification.notificationId)} className="btn btn-info btn-sm">View </button>
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

export default ListNotificationComponent
