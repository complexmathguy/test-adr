import React, { Component } from 'react'
import NotifierService from '../services/NotifierService';

class CreateNotifierComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
        }
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            NotifierService.getNotifierById(this.state.id).then( (res) =>{
                let notifier = res.data;
                this.setState({
                });
            });
        }        
    }
    saveOrUpdateNotifier = (e) => {
        e.preventDefault();
        let notifier = {
                notifierId: this.state.id,
            };
        console.log('notifier => ' + JSON.stringify(notifier));

        // step 5
        if(this.state.id === '_add'){
            notifier.notifierId=''
            NotifierService.createNotifier(notifier).then(res =>{
                this.props.history.push('/notifiers');
            });
        }else{
            NotifierService.updateNotifier(notifier).then( res => {
                this.props.history.push('/notifiers');
            });
        }
    }
    

    cancel(){
        this.props.history.push('/notifiers');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Notifier</h3>
        }else{
            return <h3 className="text-center">Update Notifier</h3>
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
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateNotifier}>Save</button>
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

export default CreateNotifierComponent
