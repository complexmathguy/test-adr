import React, { Component } from 'react'
import ReportService from '../services/ReportService'

class ViewReportComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            report: {}
        }
    }

    componentDidMount(){
        ReportService.getReportById(this.state.id).then( res => {
            this.setState({report: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Report Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> createdDateTime:&emsp; </label>
                            <div> { this.state.report.createdDateTime }</div>
                        </div>
                        <div className = "row">
                            <label> modificationDateTime:&emsp; </label>
                            <div> { this.state.report.modificationDateTime }</div>
                        </div>
                        <div className = "row">
                            <label> clientName:&emsp; </label>
                            <div> { this.state.report.clientName }</div>
                        </div>
                        <div className = "row">
                            <label> reportName:&emsp; </label>
                            <div> { this.state.report.reportName }</div>
                        </div>
                        <div className = "row">
                            <label> ObjectType:&emsp; </label>
                            <div> { this.state.report.objectType }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewReportComponent
