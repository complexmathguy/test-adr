import React, { Component } from 'react'
import ObjectOperationService from '../services/ObjectOperationService'

class ViewObjectOperationComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            objectOperation: {}
        }
    }

    componentDidMount(){
        ObjectOperationService.getObjectOperationById(this.state.id).then( res => {
            this.setState({objectOperation: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View ObjectOperation Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> callbackUrl:&emsp; </label>
                            <div> { this.state.objectOperation.callbackUrl }</div>
                        </div>
                        <div className = "row">
                            <label> bearerToken:&emsp; </label>
                            <div> { this.state.objectOperation.bearerToken }</div>
                        </div>
                        <div className = "row">
                            <label> Objects:&emsp; </label>
                            <div> { this.state.objectOperation.objects }</div>
                        </div>
                        <div className = "row">
                            <label> Operations:&emsp; </label>
                            <div> { this.state.objectOperation.operations }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewObjectOperationComponent
