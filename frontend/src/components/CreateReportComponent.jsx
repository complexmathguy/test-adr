import React, { Component } from 'react'
import ReportService from '../services/ReportService';

class CreateReportComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
                createdDateTime: '',
                modificationDateTime: '',
                clientName: '',
                reportName: '',
                objectType: ''
        }
        this.changecreatedDateTimeHandler = this.changecreatedDateTimeHandler.bind(this);
        this.changemodificationDateTimeHandler = this.changemodificationDateTimeHandler.bind(this);
        this.changeclientNameHandler = this.changeclientNameHandler.bind(this);
        this.changereportNameHandler = this.changereportNameHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
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
    }
    saveOrUpdateReport = (e) => {
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

        // step 5
        if(this.state.id === '_add'){
            report.reportId=''
            ReportService.createReport(report).then(res =>{
                this.props.history.push('/reports');
            });
        }else{
            ReportService.updateReport(report).then( res => {
                this.props.history.push('/reports');
            });
        }
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

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Report</h3>
        }else{
            return <h3 className="text-center">Update Report</h3>
        }
    }
    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.getTitle()
                                }
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> createdDateTime: </label>
                                            #formFields( $attribute, 'create')
                                            <label> modificationDateTime: </label>
                                            #formFields( $attribute, 'create')
                                            <label> clientName: </label>
                                            #formFields( $attribute, 'create')
                                            <label> reportName: </label>
                                            #formFields( $attribute, 'create')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateReport}>Save</button>
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

export default CreateReportComponent
