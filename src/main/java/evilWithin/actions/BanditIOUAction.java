package evilWithin.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import evilWithin.vfx.BanditIOUEffect;
import slimebound.SlimeboundMod;

public class BanditIOUAction extends AbstractGameAction {

    public boolean shouldDamage = false;
    public boolean shouldPlayEffect = false;
    public boolean shouldFinish = false;
    
    private BanditIOUEffect effect;
    private boolean initialized = false;

    public AbstractPlayer p;
    public AbstractMonster m;

    public int currentDamage;
    public AbstractGameEffect currentEffect;

    public BanditIOUAction(AbstractPlayer p, AbstractMonster m) {
        this.p = p;
        this.m = m;

        this.duration = 2F;
    }

    public void dealDamage(int damage){
        SlimeboundMod.logger.info("Deal damage hit");
        if (!m.isDeadOrEscaped()) {
            //SlimeboundMod.logger.info("Deal damage hitting:" + m.name);
            DamageInfo info = new DamageInfo(p, damage, DamageInfo.DamageType.THORNS);

            m.tint.color.set(Color.GRAY.cpy());
            m.tint.changeColor(Color.WHITE.cpy());
            m.damage(info);
        }
    }
    
    public void playEffect(AbstractGameEffect effect){
        SlimeboundMod.logger.info("effect:" + effect);
        AbstractDungeon.effectList.add(effect);
    }

    @Override
    public void update() {
        if (!initialized) {
            initialized = true;

            effect = new BanditIOUEffect();
            effect.action = this;
            AbstractDungeon.effectList.add(effect);
            
        } else {
            if (shouldDamage) {
                dealDamage(currentDamage);
                shouldDamage = false;
               // SlimeboundMod.logger.info("damage action received");
            }
            if (shouldPlayEffect) {
                playEffect(currentEffect);
                shouldPlayEffect = false;
                //SlimeboundMod.logger.info("effect action received");
            }
            if (shouldFinish) {
                effect.doneBlasting = true;
                isDone = true;
            }
        }

        this.tickDuration();
    }
}
