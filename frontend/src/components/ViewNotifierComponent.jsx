import React, { Component } from 'react'
import NotifierService from '../services/NotifierService'

class ViewNotifierComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            notifier: {}
        }
    }

    componentDidMount(){
        NotifierService.getNotifierById(this.state.id).then( res => {
            this.setState({notifier: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Notifier Details</h3>
                    <div className = "card-body">
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewNotifierComponent
