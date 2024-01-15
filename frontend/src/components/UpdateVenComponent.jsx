import React, { Component } from 'react'
import VenService from '../services/VenService';

class UpdateVenComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                createdDateTime: '',
                modificationDateTime: '',
                venName: '',
                objectType: ''
        }
        this.updateVen = this.updateVen.bind(this);

        this.changecreatedDateTimeHandler = this.changecreatedDateTimeHandler.bind(this);
        this.changemodificationDateTimeHandler = this.changemodificationDateTimeHandler.bind(this);
        this.changevenNameHandler = this.changevenNameHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    componentDidMount(){
        VenService.getVenById(this.state.id).then( (res) =>{
            let ven = res.data;
            this.setState({
                createdDateTime: ven.createdDateTime,
                modificationDateTime: ven.modificationDateTime,
                venName: ven.venName,
                objectType: ven.objectType
            });
        });
    }

    updateVen = (e) => {
        e.preventDefault();
        let ven = {
            venId: this.state.id,
            createdDateTime: this.state.createdDateTime,
            modificationDateTime: this.state.modificationDateTime,
            venName: this.state.venName,
            objectType: this.state.objectType
        };
        console.log('ven => ' + JSON.stringify(ven));
        console.log('id => ' + JSON.stringify(this.state.id));
        VenService.updateVen(ven).then( res => {
            this.props.history.push('/vens');
        });
    }

    changecreatedDateTimeHandler= (event) => {
        this.setState({createdDateTime: event.target.value});
    }
    changemodificationDateTimeHandler= (event) => {
        this.setState({modificationDateTime: event.target.value});
    }
    changevenNameHandler= (event) => {
        this.setState({venName: event.target.value});
    }
    changeObjectTypeHandler= (event) => {
        this.setState({objectType: event.target.value});
    }

    cancel(){
        this.props.history.push('/vens');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update Ven</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> createdDateTime: </label>
                                            #formFields( $attribute, 'update')
                                            <label> modificationDateTime: </label>
                                            #formFields( $attribute, 'update')
                                            <label> venName: </label>
                                            #formFields( $attribute, 'update')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateVen}>Save</button>
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

export default UpdateVenComponent
