package sneckomod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;
import sneckomod.powers.LacerateDebuff;
import sneckomod.powers.ToxicPersonalityPower;
import sneckomod.powers.VenomDebuff;

public class Medusa extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("Medusa");

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public Medusa() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "Medusa.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));

        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);
        addToBot(new ApplyPowerAction(m, p, new VenomDebuff(m, magicNumber), magicNumber));

        // Check if the target has LacerateDebuff and apply additional Venom equal to its amount
        if (m.hasPower(LacerateDebuff.POWER_ID) && !m.hasPower("Artifact")) {
            AbstractPower lacerate = m.getPower(LacerateDebuff.POWER_ID);
            if (lacerate != null) {
                int additionalVenomAmount = lacerate.amount;
                this.addToBot(new ApplyPowerAction(m, p, new VenomDebuff(m, additionalVenomAmount), additionalVenomAmount));
            }
        }

        // Check if the player has Toxic Personality power and the target does not have Artifact
        if (AbstractDungeon.player.hasPower(ToxicPersonalityPower.POWER_ID) && !m.hasPower("Artifact")) {
            ToxicPersonalityPower toxicPersonalityPower =
                    (ToxicPersonalityPower) AbstractDungeon.player.getPower(ToxicPersonalityPower.POWER_ID);

            if (toxicPersonalityPower != null) {
                toxicPersonalityPower.onActivateCall(m);

                // Re-check LacerateDebuff after Toxic Personality activation and apply Venom if needed
                if (m.hasPower(LacerateDebuff.POWER_ID) && !m.hasPower("Artifact")) {
                    AbstractPower lacerate = m.getPower(LacerateDebuff.POWER_ID);
                    int additionalVenomAmount = lacerate.amount;
                    this.addToBot(new ApplyPowerAction(m, p, new VenomDebuff(m, additionalVenomAmount), additionalVenomAmount));
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}
