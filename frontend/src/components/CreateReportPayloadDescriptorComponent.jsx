import React, { Component } from 'react'
import ReportPayloadDescriptorService from '../services/ReportPayloadDescriptorService';

class CreateReportPayloadDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
                payloadType: '',
                readingType: '',
                units: '',
                accuracy: '',
                confidence: '',
                objectType: ''
        }
        this.changepayloadTypeHandler = this.changepayloadTypeHandler.bind(this);
        this.changereadingTypeHandler = this.changereadingTypeHandler.bind(this);
        this.changeunitsHandler = this.changeunitsHandler.bind(this);
        this.changeaccuracyHandler = this.changeaccuracyHandler.bind(this);
        this.changeconfidenceHandler = this.changeconfidenceHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            ReportPayloadDescriptorService.getReportPayloadDescriptorById(this.state.id).then( (res) =>{
                let reportPayloadDescriptor = res.data;
                this.setState({
                    payloadType: reportPayloadDescriptor.payloadType,
                    readingType: reportPayloadDescriptor.readingType,
                    units: reportPayloadDescriptor.units,
                    accuracy: reportPayloadDescriptor.accuracy,
                    confidence: reportPayloadDescriptor.confidence,
                    objectType: reportPayloadDescriptor.objectType
                });
            });
        }        
    }
    saveOrUpdateReportPayloadDescriptor = (e) => {
        e.preventDefault();
        let reportPayloadDescriptor = {
                reportPayloadDescriptorId: this.state.id,
                payloadType: this.state.payloadType,
                readingType: this.state.readingType,
                units: this.state.units,
                accuracy: this.state.accuracy,
                confidence: this.state.confidence,
                objectType: this.state.objectType
            };
        console.log('reportPayloadDescriptor => ' + JSON.stringify(reportPayloadDescriptor));

        // step 5
        if(this.state.id === '_add'){
            reportPayloadDescriptor.reportPayloadDescriptorId=''
            ReportPayloadDescriptorService.createReportPayloadDescriptor(reportPayloadDescriptor).then(res =>{
                this.props.history.push('/reportPayloadDescriptors');
            });
        }else{
            ReportPayloadDescriptorService.updateReportPayloadDescriptor(reportPayloadDescriptor).then( res => {
                this.props.history.push('/reportPayloadDescriptors');
            });
        }
    }
    
    changepayloadTypeHandler= (event) => {
        this.setState({payloadType: event.target.value});
    }
    changereadingTypeHandler= (event) => {
        this.setState({readingType: event.target.value});
    }
    changeunitsHandler= (event) => {
        this.setState({units: event.target.value});
    }
    changeaccuracyHandler= (event) => {
        this.setState({accuracy: event.target.value});
    }
    changeconfidenceHandler= (event) => {
        this.setState({confidence: event.target.value});
    }
    changeObjectTypeHandler= (event) => {
        this.setState({objectType: event.target.value});
    }

    cancel(){
        this.props.history.push('/reportPayloadDescriptors');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add ReportPayloadDescriptor</h3>
        }else{
            return <h3 className="text-center">Update ReportPayloadDescriptor</h3>
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
                                            <label> payloadType: </label>
                                            #formFields( $attribute, 'create')
                                            <label> readingType: </label>
                                            #formFields( $attribute, 'create')
                                            <label> units: </label>
                                            #formFields( $attribute, 'create')
                                            <label> accuracy: </label>
                                            #formFields( $attribute, 'create')
                                            <label> confidence: </label>
                                            #formFields( $attribute, 'create')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateReportPayloadDescriptor}>Save</button>
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

export default CreateReportPayloadDescriptorComponent
