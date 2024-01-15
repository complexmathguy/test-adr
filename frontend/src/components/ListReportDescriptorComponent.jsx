import React, { Component } from 'react'
import ReportDescriptorService from '../services/ReportDescriptorService'

class ListReportDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                reportDescriptors: []
        }
        this.addReportDescriptor = this.addReportDescriptor.bind(this);
        this.editReportDescriptor = this.editReportDescriptor.bind(this);
        this.deleteReportDescriptor = this.deleteReportDescriptor.bind(this);
    }

    deleteReportDescriptor(id){
        ReportDescriptorService.deleteReportDescriptor(id).then( res => {
            this.setState({reportDescriptors: this.state.reportDescriptors.filter(reportDescriptor => reportDescriptor.reportDescriptorId !== id)});
        });
    }
    viewReportDescriptor(id){
        this.props.history.push(`/view-reportDescriptor/${id}`);
    }
    editReportDescriptor(id){
        this.props.history.push(`/add-reportDescriptor/${id}`);
    }

    componentDidMount(){
        ReportDescriptorService.getReportDescriptors().then((res) => {
            this.setState({ reportDescriptors: res.data});
        });
    }

    addReportDescriptor(){
        this.props.history.push('/add-reportDescriptor/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">ReportDescriptor List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addReportDescriptor}> Add ReportDescriptor</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> PayloadType </th>
                                    <th> ReadingType </th>
                                    <th> Units </th>
                                    <th> Aggregate </th>
                                    <th> StartInterval </th>
                                    <th> NumIntervals </th>
                                    <th> Historical </th>
                                    <th> Frequency </th>
                                    <th> Repeat </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.reportDescriptors.map(
                                        reportDescriptor => 
                                        <tr key = {reportDescriptor.reportDescriptorId}>
                                             <td> { reportDescriptor.payloadType } </td>
                                             <td> { reportDescriptor.readingType } </td>
                                             <td> { reportDescriptor.units } </td>
                                             <td> { reportDescriptor.aggregate } </td>
                                             <td> { reportDescriptor.startInterval } </td>
                                             <td> { reportDescriptor.numIntervals } </td>
                                             <td> { reportDescriptor.historical } </td>
                                             <td> { reportDescriptor.frequency } </td>
                                             <td> { reportDescriptor.repeat } </td>
                                             <td>
                                                 <button onClick={ () => this.editReportDescriptor(reportDescriptor.reportDescriptorId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteReportDescriptor(reportDescriptor.reportDescriptorId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewReportDescriptor(reportDescriptor.reportDescriptorId)} className="btn btn-info btn-sm">View </button>
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

export default ListReportDescriptorComponent
