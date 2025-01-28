package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.green.CripplingPoison;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import sneckomod.SneckoMod;
import sneckomod.powers.LacerateDebuff;

public class Lacerate extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("Lacerate");

    private static final int DAMAGE = 9;
    private static final int COST = 1;
    private static final int UPGRADE_MAGIC = 2;
    private static final int MAGIC = 5;

    public Lacerate() {
        super(ID, COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        this.cardsToPreview = new CripplingPoison();
        SneckoMod.loadJokeCardImage(this, "Lacerate.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(m, p, new LacerateDebuff(m, magicNumber), magicNumber));
    }

    @Override
    public void onObtainCard() {
        AbstractDungeon.topLevelEffectsQueue.add(
                new ShowCardAndObtainEffect(new CripplingPoison(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F)
        );
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }
}
