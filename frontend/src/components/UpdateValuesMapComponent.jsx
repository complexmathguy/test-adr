import React, { Component } from 'react'
import ValuesMapService from '../services/ValuesMapService';

class UpdateValuesMapComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                type: '',
                values: ''
        }
        this.updateValuesMap = this.updateValuesMap.bind(this);

        this.changetypeHandler = this.changetypeHandler.bind(this);
        this.changevaluesHandler = this.changevaluesHandler.bind(this);
    }

    componentDidMount(){
        ValuesMapService.getValuesMapById(this.state.id).then( (res) =>{
            let valuesMap = res.data;
            this.setState({
                type: valuesMap.type,
                values: valuesMap.values
            });
        });
    }

    updateValuesMap = (e) => {
        e.preventDefault();
        let valuesMap = {
            valuesMapId: this.state.id,
            type: this.state.type,
            values: this.state.values
        };
        console.log('valuesMap => ' + JSON.stringify(valuesMap));
        console.log('id => ' + JSON.stringify(this.state.id));
        ValuesMapService.updateValuesMap(valuesMap).then( res => {
            this.props.history.push('/valuesMaps');
        });
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

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update ValuesMap</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> type: </label>
                                            #formFields( $attribute, 'update')
                                            <label> values: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateValuesMap}>Save</button>
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

export default UpdateValuesMapComponent
