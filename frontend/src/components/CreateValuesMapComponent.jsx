import React, { Component } from 'react'
import ValuesMapService from '../services/ValuesMapService';

class CreateValuesMapComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
                type: '',
                values: ''
        }
        this.changetypeHandler = this.changetypeHandler.bind(this);
        this.changevaluesHandler = this.changevaluesHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            ValuesMapService.getValuesMapById(this.state.id).then( (res) =>{
                let valuesMap = res.data;
                this.setState({
                    type: valuesMap.type,
                    values: valuesMap.values
                });
            });
        }        
    }
    saveOrUpdateValuesMap = (e) => {
        e.preventDefault();
        let valuesMap = {
                valuesMapId: this.state.id,
                type: this.state.type,
                values: this.state.values
            };
        console.log('valuesMap => ' + JSON.stringify(valuesMap));

        // step 5
        if(this.state.id === '_add'){
            valuesMap.valuesMapId=''
            ValuesMapService.createValuesMap(valuesMap).then(res =>{
                this.props.history.push('/valuesMaps');
            });
        }else{
            ValuesMapService.updateValuesMap(valuesMap).then( res => {
                this.props.history.push('/valuesMaps');
            });
        }
    }
    
    changetypeHandler= (event) => {
        this.setState({type: event.target.value});
    }
    changevaluesHandler= (event) => {
        this.setState({values: event.target.value});
    }

    cancel(){
        this.props.history.push('/valuesMaps');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add ValuesMap</h3>
        }else{
            return <h3 className="text-center">Update ValuesMap</h3>
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
                                            <label> type: </label>
                                            #formFields( $attribute, 'create')
                                            <label> values: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateValuesMap}>Save</button>
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

export default CreateValuesMapComponent
