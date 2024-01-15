import React, { Component } from 'react'
import ResourceService from '../services/ResourceService';

class CreateResourceComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
                createdDateTime: '',
                modificationDateTime: '',
                resourceName: '',
                objectType: ''
        }
        this.changecreatedDateTimeHandler = this.changecreatedDateTimeHandler.bind(this);
        this.changemodificationDateTimeHandler = this.changemodificationDateTimeHandler.bind(this);
        this.changeresourceNameHandler = this.changeresourceNameHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
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
    }
    saveOrUpdateResource = (e) => {
        e.preventDefault();
        let resource = {
                resourceId: this.state.id,
                createdDateTime: this.state.createdDateTime,
                modificationDateTime: this.state.modificationDateTime,
                resourceName: this.state.resourceName,
                objectType: this.state.objectType
            };
        console.log('resource => ' + JSON.stringify(resource));

        // step 5
        if(this.state.id === '_add'){
            resource.resourceId=''
            ResourceService.createResource(resource).then(res =>{
                this.props.history.push('/resources');
            });
        }else{
            ResourceService.updateResource(resource).then( res => {
                this.props.history.push('/resources');
            });
        }
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

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Resource</h3>
        }else{
            return <h3 className="text-center">Update Resource</h3>
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
                                            <label> createdDateTime: </label>
                                            #formFields( $attribute, 'create')
                                            <label> modificationDateTime: </label>
                                            #formFields( $attribute, 'create')
                                            <label> resourceName: </label>
                                            #formFields( $attribute, 'create')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateResource}>Save</button>
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

export default CreateResourceComponent
