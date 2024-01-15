import React, { Component } from 'react'
import ResourceService from '../services/ResourceService';

class UpdateResourceComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                createdDateTime: '',
                modificationDateTime: '',
                resourceName: '',
                objectType: ''
        }
        this.updateResource = this.updateResource.bind(this);

        this.changecreatedDateTimeHandler = this.changecreatedDateTimeHandler.bind(this);
        this.changemodificationDateTimeHandler = this.changemodificationDateTimeHandler.bind(this);
        this.changeresourceNameHandler = this.changeresourceNameHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    componentDidMount(){
        ResourceService.getResourceById(this.state.id).then( (res) =>{
            let resource = res.data;
            this.setState({
                createdDateTime: resource.createdDateTime,
                modificationDateTime: resource.modificationDateTime,
                resourceName: resource.resourceName,
                objectType: resource.objectType
            });
        });
    }

    updateResource = (e) => {
        e.preventDefault();
        let resource = {
            resourceId: this.state.id,
            createdDateTime: this.state.createdDateTime,
            modificationDateTime: this.state.modificationDateTime,
            resourceName: this.state.resourceName,
            objectType: this.state.objectType
        };
        console.log('resource => ' + JSON.stringify(resource));
        console.log('id => ' + JSON.stringify(this.state.id));
        ResourceService.updateResource(resource).then( res => {
            this.props.history.push('/resources');
        });
    }

    changecreatedDateTimeHandler= (event) => {
        this.setState({createdDateTime: event.target.value});
    }
    changemodificationDateTimeHandler= (event) => {
        this.setState({modificationDateTime: event.target.value});
    }
    changeresourceNameHandler= (event) => {
        this.setState({resourceName: event.target.value});
    }
    changeObjectTypeHandler= (event) => {
        this.setState({objectType: event.target.value});
    }

    cancel(){
        this.props.history.push('/resources');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update Resource</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> createdDateTime: </label>
                                            #formFields( $attribute, 'update')
                                            <label> modificationDateTime: </label>
                                            #formFields( $attribute, 'update')
                                            <label> resourceName: </label>
                                            #formFields( $attribute, 'update')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateResource}>Save</button>
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

export default UpdateResourceComponent
