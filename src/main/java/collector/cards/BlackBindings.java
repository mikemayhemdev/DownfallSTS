package collector.cards;

import collector.powers.DoomPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.EntangleEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class BlackBindings extends AbstractCollectorCard {
    public final static String ID = makeID(BlackBindings.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 2, 1

    public BlackBindings() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new EntangleEffect(p.hb.cX + 70.0F * Settings.scale, p.hb.cY + 10.0F * Settings.scale, m.hb.cX, m.hb.cY), 0.5F));
        applyToEnemy(m, new WeakPower(m, magicNumber, false));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractPower q : m.powers) {
                    if (q.type == AbstractPower.PowerType.DEBUFF) {
                        applyToEnemyTop(m, new DoomPower(m, secondMagic));
                    }
                }
            }
        });
    }

    public void upp() {
        upgradeSecondMagic(2);
    }

    @Override //zhs card text thing
    public void initializeDescriptionCN() {
        super.initializeDescriptionCN();
        if((Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) && this.description!=null && this.description.size()>=1 ) {
            for(int i=0; i < this.description.size(); i++){
                if(this.description.get(i).text.equals("，")) this.description.remove(i);
            }
        }
    }
}