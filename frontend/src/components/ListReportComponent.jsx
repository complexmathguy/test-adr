import React, { Component } from 'react'
import ReportService from '../services/ReportService'

class ListReportComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                reports: []
        }
        this.addReport = this.addReport.bind(this);
        this.editReport = this.editReport.bind(this);
        this.deleteReport = this.deleteReport.bind(this);
    }

    deleteReport(id){
        ReportService.deleteReport(id).then( res => {
            this.setState({reports: this.state.reports.filter(report => report.reportId !== id)});
        });
    }
    viewReport(id){
        this.props.history.push(`/view-report/${id}`);
    }
    editReport(id){
        this.props.history.push(`/add-report/${id}`);
    }

    componentDidMount(){
        ReportService.getReports().then((res) => {
            this.setState({ reports: res.data});
        });
    }

    addReport(){
        this.props.history.push('/add-report/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Report List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addReport}> Add Report</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> CreatedDateTime </th>
                                    <th> ModificationDateTime </th>
                                    <th> ClientName </th>
                                    <th> ReportName </th>
                                    <th> ObjectType </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.reports.map(
                                        report => 
                                        <tr key = {report.reportId}>
                                             <td> { report.createdDateTime } </td>
                                             <td> { report.modificationDateTime } </td>
                                             <td> { report.clientName } </td>
                                             <td> { report.reportName } </td>
                                             <td> { report.objectType } </td>
                                             <td>
                                                 <button onClick={ () => this.editReport(report.reportId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteReport(report.reportId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewReport(report.reportId)} className="btn btn-info btn-sm">View </button>
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

export default ListReportComponent
