import React, { Component } from 'react'
import ObjectOperationService from '../services/ObjectOperationService';

class UpdateObjectOperationComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                callbackUrl: '',
                bearerToken: '',
                objects: '',
                operations: ''
        }
        this.updateObjectOperation = this.updateObjectOperation.bind(this);

        this.changecallbackUrlHandler = this.changecallbackUrlHandler.bind(this);
        this.changebearerTokenHandler = this.changebearerTokenHandler.bind(this);
        this.changeObjectsHandler = this.changeObjectsHandler.bind(this);
        this.changeOperationsHandler = this.changeOperationsHandler.bind(this);
    }

    componentDidMount(){
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

    updateObjectOperation = (e) => {
        e.preventDefault();
        let objectOperation = {
            objectOperationId: this.state.id,
            callbackUrl: this.state.callbackUrl,
            bearerToken: this.state.bearerToken,
            objects: this.state.objects,
            operations: this.state.operations
        };
        console.log('objectOperation => ' + JSON.stringify(objectOperation));
        console.log('id => ' + JSON.stringify(this.state.id));
        ObjectOperationService.updateObjectOperation(objectOperation).then( res => {
            this.props.history.push('/objectOperations');
        });
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

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update ObjectOperation</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> callbackUrl: </label>
                                            #formFields( $attribute, 'update')
                                            <label> bearerToken: </label>
                                            #formFields( $attribute, 'update')
                                            <label> Objects: </label>
                                            #formFields( $attribute, 'update')
                                            <label> Operations: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateObjectOperation}>Save</button>
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

export default UpdateObjectOperationComponent
