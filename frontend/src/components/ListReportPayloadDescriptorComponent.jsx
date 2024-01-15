import React, { Component } from 'react'
import ReportPayloadDescriptorService from '../services/ReportPayloadDescriptorService'

class ListReportPayloadDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                reportPayloadDescriptors: []
        }
        this.addReportPayloadDescriptor = this.addReportPayloadDescriptor.bind(this);
        this.editReportPayloadDescriptor = this.editReportPayloadDescriptor.bind(this);
        this.deleteReportPayloadDescriptor = this.deleteReportPayloadDescriptor.bind(this);
    }

    deleteReportPayloadDescriptor(id){
        ReportPayloadDescriptorService.deleteReportPayloadDescriptor(id).then( res => {
            this.setState({reportPayloadDescriptors: this.state.reportPayloadDescriptors.filter(reportPayloadDescriptor => reportPayloadDescriptor.reportPayloadDescriptorId !== id)});
        });
    }
    viewReportPayloadDescriptor(id){
        this.props.history.push(`/view-reportPayloadDescriptor/${id}`);
    }
    editReportPayloadDescriptor(id){
        this.props.history.push(`/add-reportPayloadDescriptor/${id}`);
    }

    componentDidMount(){
        ReportPayloadDescriptorService.getReportPayloadDescriptors().then((res) => {
            this.setState({ reportPayloadDescriptors: res.data});
        });
    }

    addReportPayloadDescriptor(){
        this.props.history.push('/add-reportPayloadDescriptor/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">ReportPayloadDescriptor List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addReportPayloadDescriptor}> Add ReportPayloadDescriptor</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> PayloadType </th>
                                    <th> ReadingType </th>
                                    <th> Units </th>
                                    <th> Accuracy </th>
                                    <th> Confidence </th>
                                    <th> ObjectType </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.reportPayloadDescriptors.map(
                                        reportPayloadDescriptor => 
                                        <tr key = {reportPayloadDescriptor.reportPayloadDescriptorId}>
                                             <td> { reportPayloadDescriptor.payloadType } </td>
                                             <td> { reportPayloadDescriptor.readingType } </td>
                                             <td> { reportPayloadDescriptor.units } </td>
                                             <td> { reportPayloadDescriptor.accuracy } </td>
                                             <td> { reportPayloadDescriptor.confidence } </td>
                                             <td> { reportPayloadDescriptor.objectType } </td>
                                             <td>
                                                 <button onClick={ () => this.editReportPayloadDescriptor(reportPayloadDescriptor.reportPayloadDescriptorId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteReportPayloadDescriptor(reportPayloadDescriptor.reportPayloadDescriptorId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewReportPayloadDescriptor(reportPayloadDescriptor.reportPayloadDescriptorId)} className="btn btn-info btn-sm">View </button>
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

export default ListReportPayloadDescriptorComponent
