import React, { Component } from 'react'
import NotificationService from '../services/NotificationService'

class ViewNotificationComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            notification: {}
        }
    }

    componentDidMount(){
        NotificationService.getNotificationById(this.state.id).then( res => {
            this.setState({notification: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Notification Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> ObjectType:&emsp; </label>
                            <div> { this.state.notification.objectType }</div>
                        </div>
                        <div className = "row">
                            <label> Operation:&emsp; </label>
                            <div> { this.state.notification.operation }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewNotificationComponent
