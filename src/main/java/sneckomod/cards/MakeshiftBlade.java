package sneckomod.cards;

import automaton.cards.Undervolt;
import collector.cards.Billow;
import collector.cards.CursedWail;
import collector.cards.IllTakeThat;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.Choke;
import com.megacrit.cardcrawl.cards.green.PiercingWail;
import com.megacrit.cardcrawl.cards.purple.TalkToTheHand;
import com.megacrit.cardcrawl.cards.red.Disarm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import slimebound.cards.TongueLash;
import sneckomod.SneckoMod;
import sneckomod.relics.UnknownEgg;

import java.util.ArrayList;

public class MakeshiftBlade extends AbstractSneckoCard {
    // todo: try to make this one glow later
    public static final String ID = SneckoMod.makeID("MakeshiftBlade");

    // Card constants
    private static final int DAMAGE = 12;
    private static final int COST = 1;
    private static final int MAGIC = 3; // Initial debuff requirement
    private static final int UPGRADE_MAGIC = -1; // Reduces debuff requirement by 1
    private static int SOFTLOCK = 0;


    public MakeshiftBlade() {
        super(ID, COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        baseSilly = silly = 3;
        SneckoMod.loadJokeCardImage(this, "MakeshiftBlade.png");
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        System.out.println("DEBUG: Checking for duplicate: " + card.name);
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID) && (SOFTLOCK < 100)) {
                SOFTLOCK++;
                System.out.println("DEBUG: Duplicate detected: " + card.name);
                return true;
            }
        }
        if (SOFTLOCK >= 100) {
            System.out.println("SOFTLOCK DETECTED!!!");
        }
        System.out.println("DEBUG: No Duplicate detected: " + card.name);
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (m != null && m.powers.stream().filter(power -> power.type == AbstractPower.PowerType.DEBUFF).count() >= magicNumber) {
            addToBot(new DrawCardAction(p, this.silly));
        }
    }

    @Override
    public void onObtainCard() {
        System.out.println("DEBUG: Took Makeshift Blade.");
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicateDebuff(c -> (c.rarity != AbstractCard.CardRarity.RARE));

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onPreviewObtainCard(newCard);
            }

            System.out.println("DEBUG: Card generated: " + newCard.name);
            if (!cardListDuplicate(cardsToReward, newCard)) {
                SOFTLOCK = 0;
                cardsToReward.add(newCard.makeCopy());
                System.out.println("DEBUG: Card added: " + newCard.name);
            }
        }

        SneckoMod.addGift(cardsToReward);
        ;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            upgradeDamage(4);
        }
    }
}
