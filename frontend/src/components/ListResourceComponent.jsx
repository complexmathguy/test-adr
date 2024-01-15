import React, { Component } from 'react'
import ResourceService from '../services/ResourceService'

class ListResourceComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                resources: []
        }
        this.addResource = this.addResource.bind(this);
        this.editResource = this.editResource.bind(this);
        this.deleteResource = this.deleteResource.bind(this);
    }

    deleteResource(id){
        ResourceService.deleteResource(id).then( res => {
            this.setState({resources: this.state.resources.filter(resource => resource.resourceId !== id)});
        });
    }
    viewResource(id){
        this.props.history.push(`/view-resource/${id}`);
    }
    editResource(id){
        this.props.history.push(`/add-resource/${id}`);
    }

    componentDidMount(){
        ResourceService.getResources().then((res) => {
            this.setState({ resources: res.data});
        });
    }

    addResource(){
        this.props.history.push('/add-resource/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Resource List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addResource}> Add Resource</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> CreatedDateTime </th>
                                    <th> ModificationDateTime </th>
                                    <th> ResourceName </th>
                                    <th> ObjectType </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.resources.map(
                                        resource => 
                                        <tr key = {resource.resourceId}>
                                             <td> { resource.createdDateTime } </td>
                                             <td> { resource.modificationDateTime } </td>
                                             <td> { resource.resourceName } </td>
                                             <td> { resource.objectType } </td>
                                             <td>
                                                 <button onClick={ () => this.editResource(resource.resourceId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteResource(resource.resourceId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewResource(resource.resourceId)} className="btn btn-info btn-sm">View </button>
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

export default ListResourceComponent
