import React, { Component } from 'react'
import ProgramService from '../services/ProgramService';

class CreateProgramComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
                createdDateTime: '',
                modificationDateTime: '',
                programName: '',
                programLongName: '',
                retailerName: '',
                retailerLongName: '',
                programType: '',
                country: '',
                principalSubdivision: '',
                timeZoneOffset: '',
                programDescriptions: '',
                bindingEvents: '',
                localPrice: '',
                objectType: ''
        }
        this.changecreatedDateTimeHandler = this.changecreatedDateTimeHandler.bind(this);
        this.changemodificationDateTimeHandler = this.changemodificationDateTimeHandler.bind(this);
        this.changeprogramNameHandler = this.changeprogramNameHandler.bind(this);
        this.changeprogramLongNameHandler = this.changeprogramLongNameHandler.bind(this);
        this.changeretailerNameHandler = this.changeretailerNameHandler.bind(this);
        this.changeretailerLongNameHandler = this.changeretailerLongNameHandler.bind(this);
        this.changeprogramTypeHandler = this.changeprogramTypeHandler.bind(this);
        this.changecountryHandler = this.changecountryHandler.bind(this);
        this.changeprincipalSubdivisionHandler = this.changeprincipalSubdivisionHandler.bind(this);
        this.changetimeZoneOffsetHandler = this.changetimeZoneOffsetHandler.bind(this);
        this.changeprogramDescriptionsHandler = this.changeprogramDescriptionsHandler.bind(this);
        this.changebindingEventsHandler = this.changebindingEventsHandler.bind(this);
        this.changelocalPriceHandler = this.changelocalPriceHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            ProgramService.getProgramById(this.state.id).then( (res) =>{
                let program = res.data;
                this.setState({
                    createdDateTime: program.createdDateTime,
                    modificationDateTime: program.modificationDateTime,
                    programName: program.programName,
                    programLongName: program.programLongName,
                    retailerName: program.retailerName,
                    retailerLongName: program.retailerLongName,
                    programType: program.programType,
                    country: program.country,
                    principalSubdivision: program.principalSubdivision,
                    timeZoneOffset: program.timeZoneOffset,
                    programDescriptions: program.programDescriptions,
                    bindingEvents: program.bindingEvents,
                    localPrice: program.localPrice,
                    objectType: program.objectType
                });
            });
        }        
    }
    saveOrUpdateProgram = (e) => {
        e.preventDefault();
        let program = {
                programId: this.state.id,
                createdDateTime: this.state.createdDateTime,
                modificationDateTime: this.state.modificationDateTime,
                programName: this.state.programName,
                programLongName: this.state.programLongName,
                retailerName: this.state.retailerName,
                retailerLongName: this.state.retailerLongName,
                programType: this.state.programType,
                country: this.state.country,
                principalSubdivision: this.state.principalSubdivision,
                timeZoneOffset: this.state.timeZoneOffset,
                programDescriptions: this.state.programDescriptions,
                bindingEvents: this.state.bindingEvents,
                localPrice: this.state.localPrice,
                objectType: this.state.objectType
            };
        console.log('program => ' + JSON.stringify(program));

        // step 5
        if(this.state.id === '_add'){
            program.programId=''
            ProgramService.createProgram(program).then(res =>{
                this.props.history.push('/programs');
            });
        }else{
            ProgramService.updateProgram(program).then( res => {
                this.props.history.push('/programs');
            });
        }
    }
    
    changecreatedDateTimeHandler= (event) => {
        this.setState({createdDateTime: event.target.value});
    }
    changemodificationDateTimeHandler= (event) => {
        this.setState({modificationDateTime: event.target.value});
    }
    changeprogramNameHandler= (event) => {
        this.setState({programName: event.target.value});
    }
    changeprogramLongNameHandler= (event) => {
        this.setState({programLongName: event.target.value});
    }
    changeretailerNameHandler= (event) => {
        this.setState({retailerName: event.target.value});
    }
    changeretailerLongNameHandler= (event) => {
        this.setState({retailerLongName: event.target.value});
    }
    changeprogramTypeHandler= (event) => {
        this.setState({programType: event.target.value});
    }
    changecountryHandler= (event) => {
        this.setState({country: event.target.value});
    }
    changeprincipalSubdivisionHandler= (event) => {
        this.setState({principalSubdivision: event.target.value});
    }
    changetimeZoneOffsetHandler= (event) => {
        this.setState({timeZoneOffset: event.target.value});
    }
    changeprogramDescriptionsHandler= (event) => {
        this.setState({programDescriptions: event.target.value});
    }
    changebindingEventsHandler= (event) => {
        this.setState({bindingEvents: event.target.value});
    }
    changelocalPriceHandler= (event) => {
        this.setState({localPrice: event.target.value});
    }
    changeObjectTypeHandler= (event) => {
        this.setState({objectType: event.target.value});
    }

    cancel(){
        this.props.history.push('/programs');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Program</h3>
        }else{
            return <h3 className="text-center">Update Program</h3>
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
                                            <label> programName: </label>
                                            #formFields( $attribute, 'create')
                                            <label> programLongName: </label>
                                            #formFields( $attribute, 'create')
                                            <label> retailerName: </label>
                                            #formFields( $attribute, 'create')
                                            <label> retailerLongName: </label>
                                            #formFields( $attribute, 'create')
                                            <label> programType: </label>
                                            #formFields( $attribute, 'create')
                                            <label> country: </label>
                                            #formFields( $attribute, 'create')
                                            <label> principalSubdivision: </label>
                                            #formFields( $attribute, 'create')
                                            <label> timeZoneOffset: </label>
                                            #formFields( $attribute, 'create')
                                            <label> programDescriptions: </label>
                                            #formFields( $attribute, 'create')
                                            <label> bindingEvents: </label>
                                            #formFields( $attribute, 'create')
                                            <label> localPrice: </label>
                                            #formFields( $attribute, 'create')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateProgram}>Save</button>
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

export default CreateProgramComponent
