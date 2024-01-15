import React, { Component } from 'react'
import VenService from '../services/VenService'

class ListVenComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                vens: []
        }
        this.addVen = this.addVen.bind(this);
        this.editVen = this.editVen.bind(this);
        this.deleteVen = this.deleteVen.bind(this);
    }

    deleteVen(id){
        VenService.deleteVen(id).then( res => {
            this.setState({vens: this.state.vens.filter(ven => ven.venId !== id)});
        });
    }
    viewVen(id){
        this.props.history.push(`/view-ven/${id}`);
    }
    editVen(id){
        this.props.history.push(`/add-ven/${id}`);
    }

    componentDidMount(){
        VenService.getVens().then((res) => {
            this.setState({ vens: res.data});
        });
    }

    addVen(){
        this.props.history.push('/add-ven/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Ven List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addVen}> Add Ven</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> CreatedDateTime </th>
                                    <th> ModificationDateTime </th>
                                    <th> VenName </th>
                                    <th> ObjectType </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.vens.map(
                                        ven => 
                                        <tr key = {ven.venId}>
                                             <td> { ven.createdDateTime } </td>
                                             <td> { ven.modificationDateTime } </td>
                                             <td> { ven.venName } </td>
                                             <td> { ven.objectType } </td>
                                             <td>
                                                 <button onClick={ () => this.editVen(ven.venId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteVen(ven.venId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewVen(ven.venId)} className="btn btn-info btn-sm">View </button>
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

export default ListVenComponent
