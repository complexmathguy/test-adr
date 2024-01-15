import React, { Component } from 'react'
import ReportService from '../services/ReportService';

class UpdateReportComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                createdDateTime: '',
                modificationDateTime: '',
                clientName: '',
                reportName: '',
                objectType: ''
        }
        this.updateReport = this.updateReport.bind(this);

        this.changecreatedDateTimeHandler = this.changecreatedDateTimeHandler.bind(this);
        this.changemodificationDateTimeHandler = this.changemodificationDateTimeHandler.bind(this);
        this.changeclientNameHandler = this.changeclientNameHandler.bind(this);
        this.changereportNameHandler = this.changereportNameHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    componentDidMount(){
        ReportService.getReportById(this.state.id).then( (res) =>{
            let report = res.data;
            this.setState({
                createdDateTime: report.createdDateTime,
                modificationDateTime: report.modificationDateTime,
                clientName: report.clientName,
                reportName: report.reportName,
                objectType: report.objectType
            });
        });
    }

    updateReport = (e) => {
        e.preventDefault();
        let report = {
            reportId: this.state.id,
            createdDateTime: this.state.createdDateTime,
            modificationDateTime: this.state.modificationDateTime,
            clientName: this.state.clientName,
            reportName: this.state.reportName,
            objectType: this.state.objectType
        };
        console.log('report => ' + JSON.stringify(report));
        console.log('id => ' + JSON.stringify(this.state.id));
        ReportService.updateReport(report).then( res => {
            this.props.history.push('/reports');
        });
    }

    changecreatedDateTimeHandler= (event) => {
        this.setState({createdDateTime: event.target.value});
    }
    changemodificationDateTimeHandler= (event) => {
        this.setState({modificationDateTime: event.target.value});
    }
    changeclientNameHandler= (event) => {
        this.setState({clientName: event.target.value});
    }
    changereportNameHandler= (event) => {
        this.setState({reportName: event.target.value});
    }
    changeObjectTypeHandler= (event) => {
        this.setState({objectType: event.target.value});
    }

    cancel(){
        this.props.history.push('/reports');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update Report</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> createdDateTime: </label>
                                            #formFields( $attribute, 'update')
                                            <label> modificationDateTime: </label>
                                            #formFields( $attribute, 'update')
                                            <label> clientName: </label>
                                            #formFields( $attribute, 'update')
                                            <label> reportName: </label>
                                            #formFields( $attribute, 'update')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateReport}>Save</button>
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

export default UpdateReportComponent
