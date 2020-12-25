package automaton.cards;

import automaton.AutomatonMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import downfall.util.EtherealMod;

public class DigitalCarnage extends AbstractBronzeCard {

    public final static String ID = makeID("DigitalCarnage");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 20;
    private static final int UPG_DAMAGE = 8;

    public DigitalCarnage() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        isEthereal = true;
        baseDamage = DAMAGE;
        thisEncodes();
        tags.add(AutomatonMod.BAD_COMPILE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int i;
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED)));

            for(i = 0; i < 5; ++i) {
                this.addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        } else {
            this.addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED), 0.4F));

            for(i = 0; i < 5; ++i) {
                this.addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        }


        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay, int count) {
        super.onCompile(function, forGameplay, count);
        CardModifierManager.addModifier(function, new EtherealMod());
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}