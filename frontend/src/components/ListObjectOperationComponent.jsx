import React, { Component } from 'react'
import ObjectOperationService from '../services/ObjectOperationService'

class ListObjectOperationComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                objectOperations: []
        }
        this.addObjectOperation = this.addObjectOperation.bind(this);
        this.editObjectOperation = this.editObjectOperation.bind(this);
        this.deleteObjectOperation = this.deleteObjectOperation.bind(this);
    }

    deleteObjectOperation(id){
        ObjectOperationService.deleteObjectOperation(id).then( res => {
            this.setState({objectOperations: this.state.objectOperations.filter(objectOperation => objectOperation.objectOperationId !== id)});
        });
    }
    viewObjectOperation(id){
        this.props.history.push(`/view-objectOperation/${id}`);
    }
    editObjectOperation(id){
        this.props.history.push(`/add-objectOperation/${id}`);
    }

    componentDidMount(){
        ObjectOperationService.getObjectOperations().then((res) => {
            this.setState({ objectOperations: res.data});
        });
    }

    addObjectOperation(){
        this.props.history.push('/add-objectOperation/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">ObjectOperation List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addObjectOperation}> Add ObjectOperation</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> CallbackUrl </th>
                                    <th> BearerToken </th>
                                    <th> Objects </th>
                                    <th> Operations </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.objectOperations.map(
                                        objectOperation => 
                                        <tr key = {objectOperation.objectOperationId}>
                                             <td> { objectOperation.callbackUrl } </td>
                                             <td> { objectOperation.bearerToken } </td>
                                             <td> { objectOperation.objects } </td>
                                             <td> { objectOperation.operations } </td>
                                             <td>
                                                 <button onClick={ () => this.editObjectOperation(objectOperation.objectOperationId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteObjectOperation(objectOperation.objectOperationId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewObjectOperation(objectOperation.objectOperationId)} className="btn btn-info btn-sm">View </button>
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

export default ListObjectOperationComponent
