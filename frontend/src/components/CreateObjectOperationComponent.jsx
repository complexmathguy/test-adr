import React, { Component } from 'react'
import ObjectOperationService from '../services/ObjectOperationService';

class CreateObjectOperationComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
                callbackUrl: '',
                bearerToken: '',
                objects: '',
                operations: ''
        }
        this.changecallbackUrlHandler = this.changecallbackUrlHandler.bind(this);
        this.changebearerTokenHandler = this.changebearerTokenHandler.bind(this);
        this.changeObjectsHandler = this.changeObjectsHandler.bind(this);
        this.changeOperationsHandler = this.changeOperationsHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            ObjectOperationService.getObjectOperationById(this.state.id).then( (res) =>{
                let objectOperation = res.data;
                this.setState({
                    callbackUrl: objectOperation.callbackUrl,
                    bearerToken: objectOperation.bearerToken,
                    objects: objectOperation.objects,
                    operations: objectOperation.operations
                });
            });
        }        
    }
    saveOrUpdateObjectOperation = (e) => {
        e.preventDefault();
        let objectOperation = {
                objectOperationId: this.state.id,
                callbackUrl: this.state.callbackUrl,
                bearerToken: this.state.bearerToken,
                objects: this.state.objects,
                operations: this.state.operations
            };
        console.log('objectOperation => ' + JSON.stringify(objectOperation));

        // step 5
        if(this.state.id === '_add'){
            objectOperation.objectOperationId=''
            ObjectOperationService.createObjectOperation(objectOperation).then(res =>{
                this.props.history.push('/objectOperations');
            });
        }else{
            ObjectOperationService.updateObjectOperation(objectOperation).then( res => {
                this.props.history.push('/objectOperations');
            });
        }
    }
    
    changecallbackUrlHandler= (event) => {
        this.setState({callbackUrl: event.target.value});
    }
    changebearerTokenHandler= (event) => {
        this.setState({bearerToken: event.target.value});
    }
    changeObjectsHandler= (event) => {
        this.setState({objects: event.target.value});
    }
    changeOperationsHandler= (event) => {
        this.setState({operations: event.target.value});
    }

    cancel(){
        this.props.history.push('/objectOperations');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add ObjectOperation</h3>
        }else{
            return <h3 className="text-center">Update ObjectOperation</h3>
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
                                            <label> callbackUrl: </label>
                                            #formFields( $attribute, 'create')
                                            <label> bearerToken: </label>
                                            #formFields( $attribute, 'create')
                                            <label> Objects: </label>
                                            #formFields( $attribute, 'create')
                                            <label> Operations: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateObjectOperation}>Save</button>
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

export default CreateObjectOperationComponent
