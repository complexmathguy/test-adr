import React, { Component } from 'react'
import PayloadDescriptorService from '../services/PayloadDescriptorService'

class ListPayloadDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                payloadDescriptors: []
        }
        this.addPayloadDescriptor = this.addPayloadDescriptor.bind(this);
        this.editPayloadDescriptor = this.editPayloadDescriptor.bind(this);
        this.deletePayloadDescriptor = this.deletePayloadDescriptor.bind(this);
    }

    deletePayloadDescriptor(id){
        PayloadDescriptorService.deletePayloadDescriptor(id).then( res => {
            this.setState({payloadDescriptors: this.state.payloadDescriptors.filter(payloadDescriptor => payloadDescriptor.payloadDescriptorId !== id)});
        });
    }
    viewPayloadDescriptor(id){
        this.props.history.push(`/view-payloadDescriptor/${id}`);
    }
    editPayloadDescriptor(id){
        this.props.history.push(`/add-payloadDescriptor/${id}`);
    }

    componentDidMount(){
        PayloadDescriptorService.getPayloadDescriptors().then((res) => {
            this.setState({ payloadDescriptors: res.data});
        });
    }

    addPayloadDescriptor(){
        this.props.history.push('/add-payloadDescriptor/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">PayloadDescriptor List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addPayloadDescriptor}> Add PayloadDescriptor</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.payloadDescriptors.map(
                                        payloadDescriptor => 
                                        <tr key = {payloadDescriptor.payloadDescriptorId}>
                                             <td>
                                                 <button onClick={ () => this.editPayloadDescriptor(payloadDescriptor.payloadDescriptorId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deletePayloadDescriptor(payloadDescriptor.payloadDescriptorId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewPayloadDescriptor(payloadDescriptor.payloadDescriptorId)} className="btn btn-info btn-sm">View </button>
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

export default ListPayloadDescriptorComponent
