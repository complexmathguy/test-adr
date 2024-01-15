import React, { Component } from 'react'
import ReportPayloadDescriptorService from '../services/ReportPayloadDescriptorService';

class UpdateReportPayloadDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                payloadType: '',
                readingType: '',
                units: '',
                accuracy: '',
                confidence: '',
                objectType: ''
        }
        this.updateReportPayloadDescriptor = this.updateReportPayloadDescriptor.bind(this);

        this.changepayloadTypeHandler = this.changepayloadTypeHandler.bind(this);
        this.changereadingTypeHandler = this.changereadingTypeHandler.bind(this);
        this.changeunitsHandler = this.changeunitsHandler.bind(this);
        this.changeaccuracyHandler = this.changeaccuracyHandler.bind(this);
        this.changeconfidenceHandler = this.changeconfidenceHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    componentDidMount(){
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

    updateReportPayloadDescriptor = (e) => {
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
        console.log('id => ' + JSON.stringify(this.state.id));
        ReportPayloadDescriptorService.updateReportPayloadDescriptor(reportPayloadDescriptor).then( res => {
            this.props.history.push('/reportPayloadDescriptors');
        });
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

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update ReportPayloadDescriptor</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> payloadType: </label>
                                            #formFields( $attribute, 'update')
                                            <label> readingType: </label>
                                            #formFields( $attribute, 'update')
                                            <label> units: </label>
                                            #formFields( $attribute, 'update')
                                            <label> accuracy: </label>
                                            #formFields( $attribute, 'update')
                                            <label> confidence: </label>
                                            #formFields( $attribute, 'update')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateReportPayloadDescriptor}>Save</button>
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

export default UpdateReportPayloadDescriptorComponent
