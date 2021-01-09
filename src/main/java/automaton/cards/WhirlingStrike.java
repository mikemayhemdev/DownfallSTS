package automaton.cards;

import automaton.vfx.PiercingShotEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WhirlingStrike extends AbstractBronzeCard {

    public final static String ID = makeID("WhirlingStrike");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;

    public WhirlingStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        thisEncodes();
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY", .75F, true));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new PiercingShotEffect(p.hb.cX, p.hb.cY, Settings.WIDTH + 100F, p.hb.cY), 0F));
        allDmg(AbstractGameAction.AttackEffect.NONE);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}