package collector.cards.collectibles;

import collector.powers.collectioncards.SentryPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class SentryCard extends AbstractCollectibleCard {
    public final static String ID = makeID(SentryCard.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 8, 2, , , 2, 1

    public SentryCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 1;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY), 0.3F));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        applyToEnemy(m, new WeakPower(m, magicNumber, false));

        if (AbstractDungeon.player.hasPower(SentryPower.POWER_ID)) {
            dmg(m, AbstractGameAction.AttackEffect.NONE);
            applyToEnemy(m, new WeakPower(m, magicNumber, false));
            atb(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.getPower(SentryPower.POWER_ID)));
        }
    }

    public void upp() {
        upgradeDamage(3);
        //upgradeMagicNumber(1);
    }
}