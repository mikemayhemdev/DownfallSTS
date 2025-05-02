package sneckomod.cards;

import champ.cards.Strike;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.CripplingPoison;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import sneckomod.SneckoMod;
import sneckomod.powers.LacerateDebuff;
import sneckomod.relics.UnknownEgg;

import java.util.ArrayList;

public class Lacerate extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("Lacerate");

    private static final int DAMAGE = 8;
    private static final int COST = 1;
    private static final int UPGRADE_MAGIC = 1;
    private static final int MAGIC = 3;

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
            ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
            AbstractCard r = new CripplingPoison();

            for (AbstractRelic relic : AbstractDungeon.player.relics) {
                relic.onPreviewObtainCard(r);
            }

            cardsToReward.add(r.makeCopy());
            SneckoMod.addGift(cardsToReward);
        }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }
}
