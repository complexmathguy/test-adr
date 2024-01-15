import React, { Component } from 'react'
import VenService from '../services/VenService';

class CreateVenComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
                createdDateTime: '',
                modificationDateTime: '',
                venName: '',
                objectType: ''
        }
        this.changecreatedDateTimeHandler = this.changecreatedDateTimeHandler.bind(this);
        this.changemodificationDateTimeHandler = this.changemodificationDateTimeHandler.bind(this);
        this.changevenNameHandler = this.changevenNameHandler.bind(this);
        this.changeObjectTypeHandler = this.changeObjectTypeHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
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
    }
    saveOrUpdateVen = (e) => {
        e.preventDefault();
        let ven = {
                venId: this.state.id,
                createdDateTime: this.state.createdDateTime,
                modificationDateTime: this.state.modificationDateTime,
                venName: this.state.venName,
                objectType: this.state.objectType
            };
        console.log('ven => ' + JSON.stringify(ven));

        // step 5
        if(this.state.id === '_add'){
            ven.venId=''
            VenService.createVen(ven).then(res =>{
                this.props.history.push('/vens');
            });
        }else{
            VenService.updateVen(ven).then( res => {
                this.props.history.push('/vens');
            });
        }
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

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Ven</h3>
        }else{
            return <h3 className="text-center">Update Ven</h3>
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
                                            <label> venName: </label>
                                            #formFields( $attribute, 'create')
                                            <label> ObjectType: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateVen}>Save</button>
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

export default CreateVenComponent
