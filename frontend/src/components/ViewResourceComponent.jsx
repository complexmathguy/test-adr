import React, { Component } from 'react'
import ResourceService from '../services/ResourceService'

class ViewResourceComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            resource: {}
        }
    }

    componentDidMount(){
        ResourceService.getResourceById(this.state.id).then( res => {
            this.setState({resource: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Resource Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> createdDateTime:&emsp; </label>
                            <div> { this.state.resource.createdDateTime }</div>
                        </div>
                        <div className = "row">
                            <label> modificationDateTime:&emsp; </label>
                            <div> { this.state.resource.modificationDateTime }</div>
                        </div>
                        <div className = "row">
                            <label> resourceName:&emsp; </label>
                            <div> { this.state.resource.resourceName }</div>
                        </div>
                        <div className = "row">
                            <label> ObjectType:&emsp; </label>
                            <div> { this.state.resource.objectType }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewResourceComponent
